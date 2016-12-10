<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!-- format the dates -->
<fmt:formatDate value="${messages[0].dateSent}" var="formattedDateSent"
	type="date" pattern="HH:mm, dd.MM.yyyy" />

<c:import url="template/~top.jsp" />
<c:import url="template/~header_wo_search.jsp" />

<main role="main">
	<c:import url="template/~top_bar.jsp">
		<c:param name="instr" value="Messages..." />
	</c:import>
	<div class="container">
		<div class="row">
			<div class="span-third">
				<div class="row">
					<nav class="nav-primary" id="folders">
						<ul class="menu-primary">
							<li><span id="inbox">Inbox</span></li>
							<li><span id="newMessage">New</span></li>
							<li><span id="sent">Sent</span></li>
						</ul>
					</nav>
				</div>
			</div>
			<div class="span-two-thirds">
				<div class="row">
					<div class="span-full page-half-minus-2-height">
						<div class="container-scroll">
							<div id="messageList">
								<c:choose>
									<c:when test="${not empty messages}">
										<table class="styledTable">
											<tr>
												<th id="subjectColumn">Subject</th>
												<th>Sender</th>
												<th>Recipient</th>
												<th>Date sent</th>
											</tr>
											<c:forEach items="${messages }" var="message">
												<fmt:formatDate value="${message.dateSent}"
													var="singleFormattedDateSent" type="date"
													pattern="HH:mm, dd.MM.yyyy" />
												<tr data-id="${message.id}" class="${message.state}">
													<td><span class="fa fa-circle" aria-hidden="true"></span>${message.subject }</td>
													<td>${message.sender.email}</td>
													<td>${message.recipient.email }</td>
													<td>${singleFormattedDateSent}</td>
												</tr>
											</c:forEach>
										</table>
									</c:when>
									<c:otherwise>
										<table class="styledTable">
											<tr><td>You have no messages on your inbox</td></tr>
										</table>
									</c:otherwise>
								</c:choose>
							</div>
						</div> <%-- .container-scroll END --%>
					</div> <%-- .span-full END --%>
					<div class="span-full page-half-plus-1-height messageDetail">
						<div class="form form-search form-messages form-half-plus-1-height">
							<c:import url="getMessageForm.jsp" />
						</div>
						<div class="container-scroll container-pad" style="padding-left: 0">
							<c:choose>
								<c:when test="${not empty messages}">
									<div id="messageDetail">
										<h3>${messages[0].subject }</h3>
										<ul>
											<li><b>To: </b>${messages[0].recipient.email }</li>
											<li><b>From: </b> ${messages[0].sender.email }</li>
											<li><b>Date sent:</b> ${formattedDateSent}</li>
										</ul>
										<p>${messages[0].text }</p>
									</div>
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
						</div> <%-- .container-scroll END --%>
					</div> <%-- .span-full END --%>
				</div> <%-- .row END --%>
			</div> <%-- .span-half END --%>
		</div> <%-- .row END --%>
	</div> <%-- .containerEND --%>
</main>

<%-- <c:import url="template/~footer.jsp" /> --%>
<c:import url="template/~bottom.jsp">
	<c:param name="js" value="messages" />
</c:import>
