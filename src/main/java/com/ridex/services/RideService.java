package com.ridex.services;

import com.ridex.dto.DriverRideBookingRes;
import com.ridex.dto.DriverRideFullInfoDto;
import com.ridex.dto.JoinedRideRes;
import com.ridex.dto.RideCreateReq;
import com.ridex.dto.RideJoinReq;
import com.ridex.dto.RideSearchDto;
import com.ridex.dto.RideSearchReq;
import com.ridex.entity.RideEntity;
import com.ridex.entity.RideJoinEntity;
import com.ridex.entity.RideStopEntity;
import com.ridex.entity.UserEntity;
import com.ridex.repository.RideJoinRepository;
import com.ridex.repository.RideRepository;
import com.ridex.repository.RideStopRepository;
import com.ridex.repository.UserRepository;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

import java.util.ArrayList;
import java.util.List;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RideService {

	
	@Autowired
    private  RideRepository rideRepository;
	
	@Autowired
    private  RideStopRepository rideStopRepository;
	
	@Autowired
    private  UserRepository userRepository;
	
	@Autowired
	private RideJoinRepository rideJoinRepo;
	
	
	
	

    public void offerRide(RideCreateReq req) {
        UserEntity driver = userRepository.findById(req.getDriverId())
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        RideEntity ride = new RideEntity();
        ride.setDriver(driver);
        ride.setFromLocation(req.getFromLocation());
        ride.setToLocation(req.getToLocation());
        ride.setDepartureTime(req.getDepartureTime());
        ride.setAvailableSeats(req.getAvailableSeats());
        ride.setPricePerSeat(req.getPricePerSeat());

        RideEntity savedRide = rideRepository.save(ride);

        // Save intermediate stops
        if (req.getStops() != null) {
            int order = 1;
            for (String stop : req.getStops()) {
                RideStopEntity stopEntity = new RideStopEntity();
                stopEntity.setRide(savedRide);
                stopEntity.setStopName(stop);
                stopEntity.setStopOrder(order++);
                rideStopRepository.save(stopEntity);
            }
        }
    }
    
    
    
    
    
    public void joinRide(RideJoinReq req) {
        RideEntity ride = rideRepository.findById(req.getRideId())
            .orElseThrow(() -> new RuntimeException("Ride not found"));

        if (ride.getAvailableSeats() < req.getSeatsRequired()) {
            throw new RuntimeException("Not enough seats available");
        }

        UserEntity user = userRepository.findById(req.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));

        RideJoinEntity join = new RideJoinEntity();
        join.setRide(ride);
        join.setUser(user);
        join.setSeatsBooked(req.getSeatsRequired());
        join.setAmountPaid(req.getAmountPaid());
        join.setPaymentType(req.getPaymentType());

        // ✅ For now, all payments are marked as PENDING
        join.setPaymentStatus("Pending");

        rideJoinRepo.save(join);

        // Update available seats
        ride.setAvailableSeats(ride.getAvailableSeats() - req.getSeatsRequired());
        rideRepository.save(ride);
    }
    
    
    
    
    
    
    public List<RideSearchDto> searchRides(RideSearchReq req) {
        List<RideEntity> rides = rideRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Subqueries for fromLocation
            if (req.getFromLocation() != null && !req.getFromLocation().isEmpty()) {
                Subquery<Integer> fromStopSubquery = query.subquery(Integer.class);
                Root<RideStopEntity> stopRoot = fromStopSubquery.from(RideStopEntity.class);
                fromStopSubquery.select(stopRoot.get("ride").get("id"))
                        .where(cb.like(cb.lower(stopRoot.get("stopName")), "%" + req.getFromLocation().toLowerCase() + "%"));

                predicates.add(
                    cb.or(
                        cb.like(cb.lower(root.get("fromLocation")), "%" + req.getFromLocation().toLowerCase() + "%"),
                        root.get("id").in(fromStopSubquery)
                    )
                );
            }

            // Subqueries for toLocation
            if (req.getToLocation() != null && !req.getToLocation().isEmpty()) {
                Subquery<Integer> toStopSubquery = query.subquery(Integer.class);
                Root<RideStopEntity> stopRoot = toStopSubquery.from(RideStopEntity.class);
                toStopSubquery.select(stopRoot.get("ride").get("id"))
                        .where(cb.like(cb.lower(stopRoot.get("stopName")), "%" + req.getToLocation().toLowerCase() + "%"));

                predicates.add(
                    cb.or(
                        cb.like(cb.lower(root.get("toLocation")), "%" + req.getToLocation().toLowerCase() + "%"),
                        root.get("id").in(toStopSubquery)
                    )
                );
            }

            if (req.getPreferredDepartureTime() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("departureTime"), req.getPreferredDepartureTime()));
            }

            if (req.getSeatsRequired() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("availableSeats"), req.getSeatsRequired()));
            }

            predicates.add(cb.equal(root.get("isCompleted"), false));

            query.orderBy(cb.asc(root.get("departureTime")));

            return cb.and(predicates.toArray(new Predicate[0]));
        });

        // Convert to safe DTO
        List<RideSearchDto> result = new ArrayList<>();
        for (RideEntity r : rides) {
            RideSearchDto dto = new RideSearchDto();
            dto.setRideId(r.getId());
            dto.setFromLocation(r.getFromLocation());
            dto.setToLocation(r.getToLocation());
            dto.setDepartureTime(r.getDepartureTime());
            dto.setAvailableSeats(r.getAvailableSeats());
            dto.setPricePerSeat(r.getPricePerSeat());
            dto.setDriverName(r.getDriver().getFullName());

            // ✅ Load stops for the ride
            List<RideStopEntity> stops = rideStopRepository.findByRideId(r.getId());
            String lowerFrom = req.getFromLocation() != null ? req.getFromLocation().toLowerCase() : "";
            String lowerTo = req.getToLocation() != null ? req.getToLocation().toLowerCase() : "";

            // ✅ Check if either input matches a stop
            boolean fromMatched = false;
            boolean toMatched = false;

            for (RideStopEntity stop : stops) {
                String stopName = stop.getStopName().toLowerCase();
                if (!fromMatched && !lowerFrom.isEmpty() && stopName.contains(lowerFrom)) {
                    dto.setMatchedStopMessage("This ride will stop at " + stop.getStopName());
                    fromMatched = true;
                }
                if (!toMatched && !lowerTo.isEmpty() && stopName.contains(lowerTo)) {
                    dto.setMatchedStopMessage("This ride will stop at " + stop.getStopName());
                    toMatched = true;
                }
            }

            result.add(dto);
        }
        
        return result;
    }
    
    
    
    
    public List<DriverRideBookingRes> getDriverBookings(int driverId) {
        List<RideEntity> rides = rideRepository.findByDriverId(driverId);
        List<DriverRideBookingRes> response = new ArrayList<>();

        for (RideEntity ride : rides) {
            int bookedSeats = rideJoinRepo.countByRideId(ride.getId());

            DriverRideBookingRes res = new DriverRideBookingRes();
            res.setRideId(ride.getId());
            res.setFromLocation(ride.getFromLocation());
            res.setToLocation(ride.getToLocation());
            res.setDepartureTime(ride.getDepartureTime());
            res.setAvailableSeats(ride.getAvailableSeats());
            res.setBookedSeats(bookedSeats); // 👈 use bookedSeats here
            res.setPricePerSeat(ride.getPricePerSeat());

            response.add(res);
        }

        return response;
    }
    
    
    
    public DriverRideFullInfoDto getRideDetailsForDriver(int rideId) {
        RideEntity ride = rideRepository.findById(rideId)
            .orElseThrow(() -> new RuntimeException("Ride not found"));

        List<RideJoinEntity> bookings = rideJoinRepo.findByRideId(rideId);

        DriverRideFullInfoDto res = new DriverRideFullInfoDto();
        res.setRideId(ride.getId());
        res.setFromLocation(ride.getFromLocation());
        res.setToLocation(ride.getToLocation());
        res.setDepartureTime(ride.getDepartureTime());
        res.setTotalSeats(ride.getTotalSeats()); // add this in your entity if missing
        res.setAvailableSeats(ride.getAvailableSeats());
        res.setPricePerSeat(ride.getPricePerSeat());

        int totalBooked = 0;
        double totalPaid = 0.0;
        List<DriverRideFullInfoDto.PassengerInfo> passengerList = new ArrayList<>();

        for (RideJoinEntity booking : bookings) {
            UserEntity user = booking.getUser();

            DriverRideFullInfoDto.PassengerInfo p = new DriverRideFullInfoDto.PassengerInfo();
            p.setName(user.getFullName());
            p.setSeatsBooked(booking.getSeatsBooked());
            p.setPaymentType(booking.getPaymentType());
            p.setAmountPaid(booking.getAmountPaid());

            String status = booking.getAmountPaid() >= (booking.getSeatsBooked() * ride.getPricePerSeat())
                    ? "Paid" : "Pending";
            p.setPaymentStatus(status);

            totalBooked += booking.getSeatsBooked();
            totalPaid += booking.getAmountPaid();

            passengerList.add(p);
        }

        res.setTotalBookedSeats(totalBooked);
        res.setTotalCollected(totalPaid);
        res.setPassengers(passengerList);

        return res;
    }
    
    
    
    
    public void markRideAsCompleted(int rideId) {
        RideEntity ride = rideRepository.findById(rideId)
            .orElseThrow(() -> new RuntimeException("Ride not found"));
        ride.setCompleted(true);
        rideRepository.save(ride);
    }
    
    
    
    
    
    public List<JoinedRideRes> getJoinedRides(int riderId) {
        List<RideJoinEntity> joined = rideJoinRepo.findByUserId(riderId);
        List<JoinedRideRes> response = new ArrayList<>();

        for (RideJoinEntity r : joined) {
            RideEntity ride = r.getRide();
            UserEntity driver = ride.getDriver();

            JoinedRideRes dto = new JoinedRideRes();
            dto.setRideId(ride.getId());
            dto.setFromLocation(ride.getFromLocation());
            dto.setToLocation(ride.getToLocation());
            dto.setDepartureTime(ride.getDepartureTime());
            dto.setSeatsBooked(r.getSeatsBooked());
            dto.setAmountPaid(r.getAmountPaid());
            dto.setPaymentType(r.getPaymentType());

            // 💸 Add logic to show if payment was made
//            String status = r.getAmountPaid() >= (r.getSeatsBooked() * ride.getPricePerSeat())
//                            ? "Paid" : "Pending";
//            dto.setPaymentStatus(status);
            dto.setPaymentStatus(r.getPaymentStatus());

            dto.setDriverName(driver.getFullName());
            dto.setCompleted(ride.isCompleted());

            response.add(dto);
        }

        return response;
    }
}
