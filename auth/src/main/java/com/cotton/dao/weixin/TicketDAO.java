package com.cotton.dao.weixin;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cotton.model.weixin.Ticket;
import com.cotton.model.weixin.Ticket.TicketType;

public interface TicketDAO extends JpaRepository<Ticket, Long> {
	
	Ticket findByTicketType(TicketType ticketType);
}