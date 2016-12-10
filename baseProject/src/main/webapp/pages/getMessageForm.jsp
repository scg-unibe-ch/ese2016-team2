<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<form:form
	id="messageForm"
	method="post"
	modelAttribute="messageForm"
	class="msgForm"
	action="#">
	<div class="container-scroll">
		<h3 style="padding-left: 0" class="edit-section-title">New Message...</h3>
		<form:input
			path="recipient"
			class="msgInput"
			type="text"
			id="receiverEmail"
			placeholder="To *" />
		<form:input
			path="subject"
			class="msgInput"
			type="text"
			id="msgSubject"
			placeholder="Subject *" />
		<form:textarea
			path="text"
			id="msgTextarea"
			rows="10"
			placeholder="Message" />
	</div>
	<div class="row">
		<div class="tile tile-half">
			<button class="submit-state-before" type="submit" id="new_messageSend">
				<span class="submit-before">Send</span>
				<span class="submit-after">Delivered</span>
				<span class="fa fa-circle-o-notch fa-spin fa-fw submitting"></span>
				<span class="sr-only">Sending...</span>
			</button>
		</div>
		<div class="tile tile-half">
			<button type="reset" id="messageCancel">Cancel</button>
		</div>
	</div>
</form:form>
