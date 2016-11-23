<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="template/~top.jsp" />
<c:import url="template/~header_wo_search.jsp" />


<main role="main">
	<c:import url="template/~top_bar.jsp">
		<c:param name="instr" value="Place an auction..." />
	</c:import>

	<div class="container">

		<div class="row">

			<div class="span-half">
				<div class="form form-search form-place form-max-height">

					<form:form
						method="post"
						modelAttribute="placeAuctionForm"
						action="/profile/placeAuction"
						id="placeAuctionForm"
						autocomplete="off"
						enctype="multipart/form-data">

						<div class="container-scroll">
							<div class="row checkboxes edit-section">
								<div class="tile tile-third">
									<form:radiobutton id="room" path="roomType" value="Room" checked="checked" />
									<label for="room">Room</label>
								</div>
								<div class="tile tile-third">
									<form:radiobutton id="studio" path="roomType" value="Studio" />
									<label for="studio">Studio</label>
								</div>
								<div class="tile tile-third">
									<form:radiobutton id="house" path="roomType" value="House" />
									<label for="house">House</label>
								</div>
							</div>


							<form:input
								type="text"
								id="field-street"
								path="street"
								placeholder="Address"
								tabindex="1" />


							<form:input
								class="edit-section"
								type="text"
								id="field-city"
								path="city"
								placeholder="City"
								tabindex="2" />
							<form:errors path="city" cssClass="validationErrorText" />


								<%-- @Jerome: for some reason it autosets '0' as value. W/A: set
								to '' by js. --%>
							<form:input
								value=""
								id="field-Prize"
								type="number"
								path="prize"
								placeholder="Minimun bid in CHF"
								tabindex="3"
								min="1" />
							<form:errors path="prize" cssClass="validationErrorText" />

							<form:input
								class="edit-section"
								value=""
								id="field-SquareFootage"
								type="number"
								path="squareFootage"
								placeholder="Space in m²"
								tabindex="4"
								min="5" />
							<form:errors path="squareFootage" cssClass="validationErrorText" />




							<h3 class="edit-section-title">
								End date
								<span>
									Choose a date and a time for the auction to end.
								</span>
							</h3>
							<div class="row dates related">
	              <div class="tile tile-half">
	                <div class="row">
	                  <div class="tile tile-full">
	                    <input
												tabindex="10"
	                      class="js-has-label"
	                      type="text"
	                      id="field-endDate"
												path="endDate"
	                      placeholder="Choose from datepicker..." />
	                  </div>
	                </div>
	              </div>
	              <div class="tile tile-half">
	                <div class="datepicker" id="dp-endDate">

	                </div>
	              </div>
	            </div>

							<div class="row times edit-section">
								<div class="tile tile-half">
									<div class="row">
										<div class="tile tile-full">
	                    <label>End: Hour</label>
	                  </div>
										<div class="tile tile-full action action-tile">
											<select id="auctionEndHour">
												<% for (int i = 0; i < 24; i++) {
													String hour = String.format("%02d", i);
													out.print("<option value=\"" + hour + "\">" + hour +"</option>");
												} %>
											</select>
										</div>
									</div>
								</div>
								<div class="tile tile-half">
									<div class="row">
										<div class="tile tile-full">
	                    <label>Minute</label>
	                  </div>
										<div class="tile tile-full action action-tile">
											<select id="auctionEndMinute">
												<% for (int i = 0; i < 60; i++) {
													String minute = String.format("%02d", i);
													out.print("<option value=\"" + minute + "\">" + minute +"</option>");
												} %>
											</select>
	                  </div>
									</div>
								</div>
							</div>


							<form:input id="field-endTime" type="hidden" path="endTime" /></td>


							<div class="row dates">
	              <div class="tile tile-half">
	                <div class="row">
	                  <div class="tile tile-full">
	                    <label>Move-in</label>
	                  </div>
	                  <div class="tile tile-full">
	                    <form:input
	                      class="js-has-label"
	                      type="text"
	                      id="field-moveInDate"
	                      path="moveInDate"
	                      tabindex="5"
	                      placeholder="Choose from datepicker..." />
	                  </div>
	                </div>
	              </div>
	              <div class="tile tile-half">
	                <div class="datepicker" id="moveInDate">

	                </div>
	              </div>
	            </div>


							<div class="row checkboxes">
								<div class="tile tile-half">
									<form:checkbox id="field-smoker" path="smokers" value="1" />
									<label for="field-smoker">Smoking allowed</label>
								</div>
								<div class="tile tile-half">
									<form:checkbox id="field-animals" path="animals" value="1" />
									<label for="field-animals">Animals allowed</label>
								</div>
							</div>

							<div class="row checkboxes">
								<div class="tile tile-half">
									<form:checkbox id="field-balcony" path="balcony" value="1" />
									<label for="field-balcony">Balcony or Patio</label>
								</div>
								<div class="tile tile-half">
									<form:checkbox id="field-garden" path="garden" value="1" />
									<label for="field-garden">Garden (co-use)</label>
								</div>
							</div>

							<div class="row checkboxes">
								<div class="tile tile-half">
									<form:checkbox id="field-cellar" path="cellar" value="1" />
									<label for="field-cellar">Cellar or Attic</label>
								</div>
								<div class="tile tile-half">
									<form:checkbox id="field-furnished" path="furnished" value="1" />
									<label for="field-furnished">Furnished</label>
								</div>
							</div>

							<div class="row checkboxes">
								<div class="tile tile-half">
									<form:checkbox id="field-cable" path="cable" value="1" />
									<label for="field-cable">Cable TV</label>
								</div>
								<div class="tile tile-half">
									<form:checkbox id="field-garage" path="garage" value="1" />
									<label for="field-garage">Garage</label>
								</div>
							</div>

							<div class="row checkboxes edit-section">
								<div class="tile tile-half">
									<form:checkbox id="field-internet" path="internet" value="1" />
									<label for="field-internet">WiFi</label>
								</div>
							</div>


							<form:input
								type="text"
								id="field-title"
								path="title"
								placeholder="Title"
								tabindex="7" />


							<form:textarea
								id="roomDescription"
								path="roomDescription"
								rows="10"
								tabindex="8"
								placeholder="Room Description" />
							<form:errors path="roomDescription" cssClass="validationErrorText" />


							<form:textarea
								class="edit-section"
								path="preferences"
								rows="5"
								tabindex="9"
								placeholder="Preferences" />


							<input
								type="hidden"
								id="field-pictures"
								accept="image/*"
								multiple="multiple" />


							<h3 class="edit-section-title">
								Viewing times
								<span>
									Choose a date and a time span, then click the
									<i class="fa fa-plus-circle" aris-hidden="true"></i> button below.
									To add another one, just change the values and click the button again.
								</span>
							</h3>
							<div class="row dates related">
								<div class="tile tile-half">
									<div class="row">
										<div class="tile tile-full">
											<input
												tabindex="10"
												class="js-has-label"
												type="text"
												id="field-visitDay"
												placeholder="Choose from datepicker..." />
										</div>
									</div>
								</div>
								<div class="tile tile-half">
									<div class="datepicker" id="visitDay">

									</div>
								</div>
							</div>


							<div class="row times fill-parent edit-section">
								<div class="tile tile-three-quarter">

									<div class="row">
										<div class="tile tile-half">
											<div class="row">
												<div class="tile tile-full">
													<label>From: Hour</label>
												</div>
												<div class="tile tile-full action action-tile">
													<select id="startHour">
														<% for (int i = 0; i < 24; i++) {
															String hour = String.format("%02d", i);
															out.print("<option value=\"" + hour + "\">" + hour +"</option>");
														} %>
													</select>
												</div>
											</div>
										</div>
										<div class="tile tile-half">
											<div class="row">
												<div class="tile tile-full">
													<label>Minute</label>
												</div>
												<div class="tile tile-full action action-tile">
													<select id="startMinutes">
														<% for (int i = 0; i < 60; i++) {
															String minute = String.format("%02d", i);
															out.print("<option value=\"" + minute + "\">" + minute +"</option>");
														} %>
													</select>
												</div>
											</div>
										</div>
									</div>



									<div class="row times">
										<div class="tile tile-half">
											<div class="row">
												<div class="tile tile-full">
													<label>To: Hour</label>
												</div>
												<div class="tile tile-full action action-tile">
													<select id="endHour">
														<% for (int i = 0; i < 24; i++) {
															String hour = String.format("%02d", i);
															out.print("<option value=\"" + hour + "\">" + hour +"</option>");
														} %>
													</select>
												</div>
											</div>
										</div>
										<div class="tile tile-half">
											<div class="row">
												<div class="tile tile-full">
													<label>Minute</label>
												</div>
												<div class="tile tile-full action action-tile">
													<select id="endMinutes">
														<% for (int i = 0; i < 60; i++) {
															String minute = String.format("%02d", i);
															out.print("<option value=\"" + minute + "\">" + minute +"</option>");
														} %>
													</select>
												</div>
											</div>
										</div>
									</div>

								</div> <%-- .tile-three-quarter END --%>
								<div class="tile tile-quarter fill-parent-child">
									<div id="addVisitButton" class="action action-icon action-add">
										<span title="Add viewing time" class="fa fa-plus-circle fa-3x"></span>
									</div>
								</div>
							</div> <%-- .row.times END --%>

						</div> <%-- .container-scroll END --%>


						<div class="row">
							<%-- Will be populated with hidden inputs --%>
							<div style="display: none" id="addedVisits"></div>

							<div class="tile tile-half">
								<button type="submit">Submit</button>
							</div>
							<div class="tile tile-half">
								<a href="/"><button type="button">Cancel</button></a>
							</div>
						</div>

					</form:form>

				</div> <%-- .form.form-search END --%>
			</div> <%-- .span-half END --%>

			<div class="span-half">
				<h3 class="edit-section-title">
					Drop images here...
					<span>
						Drag and drop images onto the window.<br>
						You can remove an image by double clicking it.
					</span>
				</h3>
				<div class="row">
					<div class="tile tile-full action-dropzone">
						<div id="image-preview"></div>
					</div>
				</div>

				<h3 class="edit-section-title">
					Your viewing times...
					<span>
						Add viewing times from the panel to your left at the bottom of the
						form. You can remove a viewing time by double clicking the corresponding
						<i class="fa fa-times base-color-opposite"></i>.
				</h3>
				<div class="row">
					<div class="tile tile-full action-viewing-delete">
						<div id="viewing-preview"></div>
					</div>
				</div>
				<div class="row">
					<div class="tile tile-half">

					</div>
				</div>
			</div>

		</div> <%-- .row END --%>
	</div> <%-- .container END --%>

</main>

<c:import url="template/~bottom.jsp">
	<c:param name="js" value="placeAuction" />
</c:import>
