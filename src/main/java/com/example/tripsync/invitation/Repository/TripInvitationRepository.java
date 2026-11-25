package com.example.tripsync.invitation.Repository;

import com.example.tripsync.invitation.Entity.InvitationStatus;
import com.example.tripsync.invitation.Entity.TripInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface TripInvitationRepository extends JpaRepository<TripInvitation, Long> {

    List<TripInvitation> findByInviteEmailIgnoreCase(String inviteEmail);

    List<TripInvitation> findByTripId(Long tripId);

    Optional<TripInvitation> findByIdAndInviteEmailIgnoreCase(
            Long id,
            String inviteEmail
    );
    boolean existsByTripIdAndInviteEmailIgnoreCaseAndStatus(
            Long tripId,
            String inviteEmail,
            InvitationStatus status
    );
}
