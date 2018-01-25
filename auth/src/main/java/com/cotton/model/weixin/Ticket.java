package com.cotton.model.weixin;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.cotton.model.base.BaseModel;

@Entity
@Table(name="t_ticket")
public class Ticket extends BaseModel {
	
	private String ticket;
	
	private Integer expireTime;
	
	@Enumerated(EnumType.STRING)
	private TicketType ticketType;
	
	public enum TicketType {
		   Jsapi
	}
	
	
	public String getTicket() {
		return ticket;
	}


	public Integer getExpireTime() {
		return expireTime;
	}


	public void setTicket(String ticket) {
		this.ticket = ticket;
	}


	public void setExpireTime(Integer expireTime) {
		this.expireTime = expireTime;
	}


	public TicketType getTicketType() {
		return ticketType;
	}


	public void setTicketType(TicketType ticketType) {
		this.ticketType = ticketType;
	}


	public boolean isExpire() {
		return System.currentTimeMillis()-getUpdateTime().getTime()>getExpireTime()*1000*0.9;
	}
}