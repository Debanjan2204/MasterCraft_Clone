package com.ded.BTS;
import java.time.Instant;

import org.springframework.boot.CommandLineRunner;

import com.ded.BTS.enums.TicketPriority;
import com.ded.BTS.enums.TicketStatus;
import com.ded.BTS.enums.TicketType;
import com.ded.BTS.enums.UserStatus;
import com.ded.BTS.model.Project;
import com.ded.BTS.model.Role;
import com.ded.BTS.model.Ticket;
import com.ded.BTS.model.TicketComment;
import com.ded.BTS.model.TicketHistory;
import com.ded.BTS.model.User;
import com.ded.BTS.model.UserRole;
import com.ded.BTS.repository.ProjectRepo;
import com.ded.BTS.repository.RoleRepo;
import com.ded.BTS.repository.TicketCommentRepo;
import com.ded.BTS.repository.TicketHistoryRepo;
import com.ded.BTS.repository.TicketRepo;
import com.ded.BTS.repository.UserRepo;
import com.ded.BTS.repository.UserRoleRepo;


public class CrudTestRunner implements CommandLineRunner {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final UserRoleRepo userRoleRepo;
    private final ProjectRepo projectRepo;
    private final TicketRepo ticketRepo;
    private final TicketCommentRepo commentRepo;
    private final TicketHistoryRepo historyRepo;

    public CrudTestRunner(UserRepo userRepo, RoleRepo roleRepo, UserRoleRepo userRoleRepo,
                          ProjectRepo projectRepo, TicketRepo ticketRepo, TicketCommentRepo commentRepo,
                          TicketHistoryRepo historyRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.userRoleRepo = userRoleRepo;
        this.projectRepo = projectRepo;
        this.ticketRepo = ticketRepo;
        this.commentRepo = commentRepo;
        this.historyRepo = historyRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== STARTING BASIC CRUD TEST ===");

        // 1️⃣ Insert Users
        User user1 = new User();
        user1.setUsername("alice");
        user1.setEmail("alice@example.com");
        user1.setPasswordHash("hash1");
        user1.setFullName("Alice Wonderland");
        user1.setStatus(UserStatus.ACTIVE);
        userRepo.save(user1);

        User user2 = new User();
        user2.setUsername("bob");
        user2.setEmail("bob@example.com");
        user2.setPasswordHash("hash2");
        user2.setFullName("Bob Builder");
        user2.setStatus(UserStatus.ACTIVE);
        userRepo.save(user2);

        System.out.println("Users inserted: " + userRepo.findAll());

        // 2️⃣ Insert Roles
        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        roleRepo.save(adminRole);

        Role devRole = new Role();
        devRole.setName("DEVELOPER");
        roleRepo.save(devRole);

        System.out.println("Roles inserted: " + roleRepo.findAll());

        // 3️⃣ Map UserRoles
        UserRole ur1 = new UserRole();
        ur1.setUser(user1);
        ur1.setRole(adminRole);
        userRoleRepo.save(ur1);

        UserRole ur2 = new UserRole();
        ur2.setUser(user2);
        ur2.setRole(devRole);
        userRoleRepo.save(ur2);

        System.out.println("UserRoles inserted: " + userRoleRepo.findAll());

        // 4️⃣ Create Project
        Project project = new Project();
        project.setProjectKey("PROJ1");
        project.setName("Ticket Management");
        project.setDescription("JIRA-like system");
        project.setOwner(user1);
        projectRepo.save(project);

        System.out.println("Projects inserted: " + projectRepo.findAll());

        // 5️⃣ Create Ticket
        Ticket ticket = new Ticket();
        ticket.setProject(project);
        ticket.setTitle("Sample Ticket");
        ticket.setDescription("This is a sample ticket");
        ticket.setType(TicketType.DEFECT);
        ticket.setPriority(TicketPriority.HIGH);
        ticket.setStatus(TicketStatus.PENDING_IT);
        ticket.setReporter(user1);
        ticket.setAssignee(user2);
        ticketRepo.save(ticket);

        System.out.println("Tickets inserted: " + ticketRepo.findAll());

        // 6️⃣ Add Ticket Comment
        TicketComment comment = new TicketComment();
        comment.setTicket(ticket);
        comment.setAuthor(user2);
        comment.setContent("Looking into this issue");
        commentRepo.save(comment);

        System.out.println("TicketComments inserted: " + commentRepo.findAll());

        // 7️⃣ Add TicketHistory
//        TicketHistory history = new TicketHistory(
//                ticket,
//                "status",
//                "PENDING_IT",
//                "IN_PROGRESS",
//                user2,
//                Instant.now()
//        );
//        historyRepo.save(history);

        System.out.println("TicketHistory inserted: " + historyRepo.findAll());

        System.out.println("=== BASIC CRUD TEST COMPLETED ===");
    }
}
