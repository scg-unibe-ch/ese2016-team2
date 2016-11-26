<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="template/~top.jsp" />
<c:import url="template/~header_wo_search.jsp" />

<main role="main">
	<c:import url="template/~top_bar.jsp">
		<c:param name="instr" value="Place an ad..." />
	</c:import>

	<div class="container">

		<div class="row">

			<div class="span-half">
				<div class="form form-search form-place form-max-height">

					<form:form
						method="post"
						modelAttribute="placeAdForm"
						action="/profile/placeAd"
						id="placeAdForm"
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

							<div class="row checkboxes edit-section">
								<div class="tile tile-half">
									<form:radiobutton id="rent" path="buyable" value="0" checked="checked" />
									<label for="rent">Rent</label>
								</div>
								<div class="tile tile-half">
									<form:radiobutton id="buy" path="buyable" value="1" />
									<label for="buy">Sell</label>
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
								placeholder="Rent or Price in CHF"
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

	            <div class="row dates fields-optional fields-optional-sell js-show edit-section">
	              <div class="tile tile-half">
	                <div class="row">
	                  <div class="tile tile-full">
	                    <label>Move-out (optional when selling)</label>
	                  </div>
	                  <div class="tile tile-full">
	                    <form:input
	                      class="js-has-label"
	                      type="text"
	                      id="field-moveOutDate"
	                      path="moveOutDate"
	                      tabindex="6"
	                      placeholder="Choose from datepicker..." />
	                  </div>
	                </div>
	              </div>
	              <div class="tile tile-half">
	                <div class="datepicker" id="moveOutDate">

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

							<div class="row times">
								<div class="tile tile-quarter">
									<label>From</label>
								</div>
								<div class="tile tile-half">
									<input class="time-range" id="startTime" type="range" value="48" min="0" max="96">
								</div>
								<div class="tile tile-quarter show-time">
									<p id="show-startTime">12:00</p>
								</div>
							</div> <%-- .row.times END --%>

							<div class="row times related">
								<div class="tile tile-quarter">
									<label>To</label>
								</div>
								<div class="tile tile-half">
									<input class="time-range" id="endTime" type="range" value="48" min="0" max="96">
								</div>
								<div class="tile tile-quarter">
									<p id="show-endTime">12:00</p>
								</div>
							</div> <%-- .row.times END --%>


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

							<div class="row times">
								<div class="tile tile-full">
									<div id="addVisitButton" class="action action-icon action-add">
										<span>Add viewing time</span>
										<%-- <span title="Add viewing time" class="fa fa-plus-circle fa-3x"></span> --%>
									</div>
								</div>
							</div>

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

			<div class="span-half page-max-height">
				<div class="container-scroll">
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

				</div> <%-- .container-scroll END --%>
			</div> <%-- .span-half END --%>

		</div> <%-- .row END --%>
	</div> <%-- .container END --%>

</main>

<%-- <c:import url="template/footer.jsp" /> --%>
<c:import url="template/~bottom.jsp">
	<c:param name="js" value="placeAd" />
</c:import>
