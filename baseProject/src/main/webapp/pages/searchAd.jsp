<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="template/~top.jsp" />
<c:import url="template/~header_wo_search.jsp" />

<main role="main">
	<c:import url="template/~top_bar.jsp">
		<c:param name="instr" value="Find..." />
	</c:import>

	<div class="container">

		<div class="row">

			<div class="span-half">
				<div class="form form-search form-max-height">
					<div class="container-scroll">

						<form:form
							method="post"
							id="searchForm"
							modelAttribute="searchForm"
							action="/results"
							autocomplete="off">

							<form:input
								type="text"
								name="city"
								id="city"
								path="city"
								placeholder="Find..."
								tabindex="1"
								value="" />
							<form:errors path="city" cssClass="validationErrorText" />

							<div class="row checkboxes">
								<div class="tile tile-half">
									<form:radiobutton name="buyable" id="rent" path="buyable" value="0" />
									<label for="rent">Rent</label>
								</div>
								<div class="tile tile-half">
									<form:radiobutton name="buyable" id="buy" path="buyable" value="1" />
									<label for="buy">Buy</label>
								</div>
							</div>


							<form:input
								id="radiusInput"
								type="number"
								path="radius"
								placeholder="Radius in km (5, 10, 15,... , 500)"
								tabindex="2"
								step="5"
								min="5"
								max="500"
								value="" />
							<form:errors path="radius" cssClass="validationErrorText" />


							<div class="row checkboxes">
								<div class="tile tile-third">
									<form:checkbox name="room" id="room" path="room" />
									<label for="room">Room</label>
								</div>
								<div class="tile tile-third">
									<form:checkbox name="studio" id="studio" path="studio" />
									<label for="studio">Studio</label>
								</div>
								<div class="tile tile-third">
									<form:checkbox name="house" id="house" path="house" />
									<label for="house">House</label>
								</div>
							</div>


							<form:checkbox style="display:none" name="neither" id="neither" path="neither" />
							<form:errors path="neither" cssClass="validationErrorText" />


							<form:input
								id="prizeInput"
								type="number"
								path="prize"
								placeholder="Maximum Price in CHF"
								step="50"
								tabindex="3"
								min="50" />
							<form:errors path="prize" cssClass="validationErrorText" />


							<div class="row dates">
	              <div class="tile tile-half">
	                <div class="row">
	                  <div class="tile tile-full">
	                    <label>Earliest Move-in</label>
	                  </div>
	                  <div class="tile tile-full">
	                    <form:input
	                      class="js-has-label"
	                      type="text"
	                      id="field-earliestMoveInDate"
	                      path="earliestMoveInDate"
	                      tabindex="4"
	                      placeholder="Choose from datepicker..." />
	                  </div>
	                </div>
	              </div>
	              <div class="tile tile-half">
	                <div class="datepicker" id="earliestMoveInDate">

	                </div>
	              </div>
	            </div>

	            <div class="row dates">
	              <div class="tile tile-half">
	                <div class="row">
	                  <div class="tile tile-full">
	                    <label>Earliest Move-out</label>
	                  </div>
	                  <div class="tile tile-full">
	                    <form:input
												style="display: none"
	                      class="js-has-label"
	                      type="text"
	                      id="field-earliestMoveOutDate"
	                      path="earliestMoveOutDate"
	                      tabindex="5"
	                      placeholder="Choose from datepicker..." />
	                  </div>
	                </div>
	              </div>
	              <div class="tile tile-half">
	                <div class="datepicker" id="earliestMoveOutDate">

	                </div>
	              </div>
	            </div>

	            <div class="row dates">
	              <div class="tile tile-half">
	                <div class="row">
	                  <div class="tile tile-full">
	                    <label>Latest Move-in</label>
	                  </div>
	                  <div class="tile tile-full">
	                    <form:input
	                      class="js-has-label"
	                      type="text"
	                      id="field-latestMoveInDate"
	                      path="latestMoveInDate"
	                      tabindex="6"
	                      placeholder="Choose from datepicker..." />
	                  </div>
	                </div>
	              </div>
	              <div class="tile tile-half">
	                <div class="datepicker" id="latestMoveInDate">

	                </div>
	              </div>
	            </div>

	            <div class="row dates">
	              <div class="tile tile-half">
	                <div class="row">
	                  <div class="tile tile-full">
	                    <label>Latest Move-out</label>
	                  </div>
	                  <div class="tile tile-full">
	                    <form:input
	                      class="js-has-label"
	                      type="text"
	                      id="field-latestMoveOutDate"
	                      path="latestMoveOutDate"
	                      tabindex="7"
	                      placeholder="Choose from datepicker..." />
	                  </div>
	                </div>
	              </div>
	              <div class="tile tile-half">
	                <div class="datepicker" id="latestMoveOutDate">

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

	            <div class="row checkboxes">
	              <div class="tile tile-half">
	                <form:checkbox id="field-internet" path="internet" value="1" />
	                <label for="field-internet">WiFi</label>
	              </div>
	            </div>

						</div> <%-- .container-scroll END --%>

	          <div class="row">
	            <div class="tile tile-half">
	              <button type="submit" tabindex="8">Find</button>
	            </div>
	            <div class="tile tile-half">
	    					<button form="searchForm" type="reset">Clear</button>
	            </div>
	          </div>

					</form:form>

				</div> <%-- .form.form-search END --%>
			</div> <%-- .span-half END --%>

		</div> <%-- .row END --%>
	</div> <%-- .container.container-pad END --%>

</main>

<%-- <c:import url="template/~footer.jsp" /> --%>
<c:import url="template/~bottom.jsp">
	<c:param name="js" value="search-ad" />
</c:import>
