package com.example.tripsync.invitation.Entity;

import com.example.tripsync.trip.entity.Trip;
import com.example.tripsync.trip.entity.TripMemberRole;
import com.example.tripsync.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripInvitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "trip_id",nullable = false)
    private Trip trip;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "invitedBy_id",nullable = false)
    private User invitedBy;
    @Column(nullable = false)
    private String inviteEmail;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private TripMemberRole proposedRole = TripMemberRole.VIEWER;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private InvitationStatus status = InvitationStatus.PENDING;
    @Column(nullable = false)
    private LocalDateTime expiredAt;
}
