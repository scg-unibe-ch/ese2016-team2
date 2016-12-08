<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:import url="template/~top.jsp" />
<c:import url="template/~header_w_map_wo_search.jsp" />

<main role="main">
	<c:import url="template/~top_bar.jsp">
		<c:param name="instr" value="Filter finds..." />
	</c:import>

<div class="container"></div>

<div class="container">

	<div class="row">

		<div class="span-half">

			<div class="form form-search form-filter form-max-height">

				<form:form method="post" modelAttribute="searchForm"
					action="/results" id="filterForm" autocomplete="off">

					<div class="container-scroll">
						<div id="map" class="row"></div>

						<form:input type="text" name="city" id="city" path="city"
							placeholder="Find..." tabindex="1" />
						<form:errors path="city" cssClass="validationErrorText" />

						<div class="row checkboxes">
							<div class="tile tile-half">
								<form:radiobutton name="buyable" id="rent" path="buyable"
									value="0" />
								<label for="rent">Rent</label>
							</div>
							<div class="tile tile-half">
								<form:radiobutton name="buyable" id="buy" path="buyable"
									value="1" />
								<label for="buy">Buy</label>
							</div>
						</div>


						<div class="row checkboxes">
							<div class="tile tile-half">
								<form:checkbox name="auction" id="auction" path="auction" />
								<label for="auction">Auction</label>
							</div>
							<div class="tile tile-half">
								<form:checkbox name="ad" id="ad" path="ad" />
								<label for="ad">Ad</label>
							</div>
						</div>


						<form:input id="radiusInput" type="number" path="radius"
							placeholder="Radius in km (5, 10, 15,... , 500)" tabindex="2"
							step="5" min="5" max="500" />
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


						<form:checkbox style="display:none" name="neither" id="neither"
							path="neither" />
						<form:errors path="neither" cssClass="validationErrorText" />


						<form:input id="prizeInput" type="number" path="prize"
							placeholder="Maximum Price in CHF" step="50" tabindex="3"
							min="50" />
						<form:errors path="prize" cssClass="validationErrorText" />


						<div class="row dates">
							<div class="tile tile-half">
								<div class="row">
									<div class="tile tile-full">
										<label>Earliest Move-in</label>
									</div>
									<div class="tile tile-full">
										<form:input class="js-has-label" type="text"
											id="field-earliestMoveInDate" path="earliestMoveInDate"
											tabindex="4" placeholder="Choose from datepicker..." />
									</div>
								</div>
							</div>
							<div class="tile tile-half">
								<div class="datepicker" id="earliestMoveInDate"></div>
							</div>
						</div>

						<div class="row dates">
							<div class="tile tile-half">
								<div class="row">
									<div class="tile tile-full">
										<label>Earliest Move-out</label>
									</div>
									<div class="tile tile-full">
										<form:input style="display: none" class="js-has-label"
											type="text" id="field-earliestMoveOutDate"
											path="earliestMoveOutDate" tabindex="5"
											placeholder="Choose from datepicker..." />
									</div>
								</div>
							</div>
							<div class="tile tile-half">
								<div class="datepicker" id="earliestMoveOutDate"></div>
							</div>
						</div>

						<div class="row dates">
							<div class="tile tile-half">
								<div class="row">
									<div class="tile tile-full">
										<label>Latest Move-in</label>
									</div>
									<div class="tile tile-full">
										<form:input class="js-has-label" type="text"
											id="field-latestMoveInDate" path="latestMoveInDate"
											tabindex="6" placeholder="Choose from datepicker..." />
									</div>
								</div>
							</div>
							<div class="tile tile-half">
								<div class="datepicker" id="latestMoveInDate"></div>
							</div>
						</div>

						<div class="row dates">
							<div class="tile tile-half">
								<div class="row">
									<div class="tile tile-full">
										<label>Latest Move-out</label>
									</div>
									<div class="tile tile-full">
										<form:input class="js-has-label" type="text"
											id="field-latestMoveOutDate" path="latestMoveOutDate"
											tabindex="7" placeholder="Choose from datepicker..." />
									</div>
								</div>
							</div>
							<div class="tile tile-half">
								<div class="datepicker" id="latestMoveOutDate"></div>
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

					</div>
					<%-- .container-scroll END --%>

					<div class="row">
						<div class="tile tile-third">
							<button type="submit" tabindex="8" name="results" value="Results">Find</button>
						</div>
						<div class="tile tile-third">
							<button form="filterForm" type="reset">Clear Filters</button>
						</div>
						<div class="tile tile-third">
							<button type="submit" tabindex="8" name="createAlert"
								value="CreateAlert">Create Alert</button>
						</div>
					</div>

				</form:form>

			</div>
			<%-- .form.form-filter END --%>
		</div>
		<%-- .span-half END --%>

		<div class="span-half list-max-height">

			<div class="row">
				<div class="tile tile-full action action-tile">
					<select class="list-sort" id="modus">
						<option value="">Sort by:</option>
						<option value="price_asc">Price (ascending)</option>
						<option value="price_desc">Price (descending)</option>
						<option value="moveIn_desc">Move-in date (earliest to
							latest)</option>
						<option value="moveIn_asc">Move-in date (latest to
							earliest)</option>
						<option value="dateAge_asc">Date created (youngest to
							oldest)</option>
						<option value="dateAge_desc">Date created (oldest to
							youngest)</option>
					</select>
				</div>
			</div>


			<div id="list" class="container-scroll">


				<c:choose>

					<c:when test="${empty results} AND ${empty premiumResults}">
						<p>No results found!
					</c:when>

					<c:otherwise>
						<ul id="resultsPremiumDiv" class="resultsDiv">

							<c:forEach var="ad" items="${premiumResults}">

								<c:choose>
									<c:when test="${!ad.auction}">

										<li class="resultPremiumAd" data-price="${ad.prize}"
											data-moveIn="${ad.moveInDate}" data-age="${ad.moveInDate}"
											data-lat="${ad.latitude}" data-lon="${ad.longitude}"
											data-title="${ad.title}"
											data-address="${ad.street},${ad.zipcode} ${ad.city}">

											<div class="row">
												<div class="tile tile-half">
													<div class="list-image">
														<a class="list-image-link"
															href="<c:url value='/ad?id=${ad.id}' />"
															style="background-image: url(${ad.pictures[0].filePath})">
														</a>
													</div>
												</div>

												<div class="tile tile-half">
													<div class="resultAd-text">

														<h2>
															<a title="Premium Offer!" class="link"
																href="<c:url value='/ad?id=${ad.id}' />"> <span
																class="fa fa-star" aria-hidden="true"></span>&nbsp;&nbsp;${ad.title }
															</a>
														</h2>

														<p>${ad.street}</p>
														<p>${ad.zipcode} ${ad.city}</p>
														<p>${ad.roomType}</p>


														<h3>CHF ${ad.prize}</h3>

														<fmt:formatDate value="${ad.moveInDate}"
															var="formattedMoveInDate" type="date"
															pattern="dd.MM.yyyy" />

														<p>Move-in date: ${formattedMoveInDate }</p>

													</div>
												</div>
											</div>

										</li>
									</c:when>

									<c:otherwise>
										<li class="resultPremiumAd" data-price="${ad.prize}"
											data-moveIn="${ad.moveInDate}" data-age="${ad.moveInDate}"
											data-lat="${ad.latitude}" data-lon="${ad.longitude}"
											data-title="${ad.title}"
											data-address="${ad.street},${ad.zipcode} ${ad.city}">

											<div class="row">
												<div class="tile tile-half">
													<div class="list-image">
														<a class="list-image-link"
															href="<c:url value='/auction?id=${ad.id}' />"
															style="background-image: url(${ad.pictures[0].filePath})">
														</a>
													</div>
												</div>

												<div class="tile tile-half">
													<div class="resultAd-text">

														<h2>
															<a title="Premium Offer!" class="link"
																href="<c:url value='/auction?id=${ad.id}' />"> <span
																class="fa fa-star" aria-hidden="true"></span> <span
																class="fa fa-gavel" aria-hidden="true"></span>
																&nbsp;&nbsp;${ad.title }
															</a>
														</h2>

														<p>${ad.street}</p>
														<p>${ad.zipcode} ${ad.city}</p>
														<p>${ad.roomType}</p>


														<h3>CHF ${ad.prize}</h3>

														<fmt:formatDate value="${ad.moveInDate}"
															var="formattedMoveInDate" type="date"
															pattern="dd.MM.yyyy" />

														<p>Move-in date: ${formattedMoveInDate }</p>
														<p>Auction end-date: ${ad.endTime}</p>
													</div>
												</div>
											</div>

										</li>
										<%-- <li
												class="resultPremiumAd"
												data-price="${ad.prize}"
												data-moveIn="${ad.moveInDate}"
												data-age="${ad.moveInDate}">

												<a
													class="list-image-link"
													href="<c:url value='/auction?id=${ad.id}' />"
													style="background-image: url(${ad.pictures[0].filePath})">
												</a>

												<div class="resultAd-text">
													<h2>
														<a class="link" href="<c:url value='/auction?id=${ad.id}' />">
															${ad.title }
														</a>
													</h2>

													<p>${ad.street},${ad.zipcode} ${ad.city}</p>
													<p>${ad.roomType}</p>


													<h3>CHF ${ad.prize}</h3>

													<fmt:formatDate value="${ad.moveInDate}"
														var="formattedMoveInDate" type="date" pattern="dd.MM.yyyy" />

													<p>Move-in date: ${formattedMoveInDate }</p>
													<p>Auction end-date: ${ad.endTime}</p>
												</div>

											</li> --%>
									</c:otherwise>

								</c:choose>
							</c:forEach>

						</ul>
						<ul id="resultsDiv" class="resultsDiv">


							<c:forEach var="ad" items="${results}">

								<c:choose>
									<c:when test="${!ad.auction}">

										<li class="resultAd" data-price="${ad.prize}"
											data-moveIn="${ad.moveInDate}" data-age="${ad.moveInDate}"
											data-lat="${ad.latitude}" data-lon="${ad.longitude}"
											data-title="${ad.title}"
											data-address="${ad.street},${ad.zipcode} ${ad.city}">

											<div class="row">
												<div class="tile tile-half">
													<div class="list-image">
														<a class="list-image-link"
															href="<c:url value='/ad?id=${ad.id}' />"
															style="background-image: url(${ad.pictures[0].filePath})">
														</a>
													</div>
												</div>

												<div class="tile tile-half">
													<div class="resultAd-text">

														<h2>
															<a class="link" href="<c:url value='/ad?id=${ad.id}' />">
																${ad.title } </a>
														</h2>

														<p>${ad.street}</p>
														<p>${ad.zipcode} ${ad.city}</p>
														<p>${ad.roomType}</p>


														<h3>CHF ${ad.prize}</h3>

														<fmt:formatDate value="${ad.moveInDate}"
															var="formattedMoveInDate" type="date"
															pattern="dd.MM.yyyy" />

														<p>Move-in date: ${formattedMoveInDate }</p>

													</div>
												</div>
											</div>

										</li>
									</c:when>

									<c:otherwise>
										<li class="resultAd" data-price="${ad.prize}"
											data-moveIn="${ad.moveInDate}" data-age="${ad.moveInDate}"
											data-lat="${ad.latitude}" data-lon="${ad.longitude}"
											data-title="${ad.title}"
											data-address="${ad.street},${ad.zipcode} ${ad.city}">

											<div class="row">
												<div class="tile tile-half">
													<div class="list-image">
														<a class="list-image-link"
															href="<c:url value='/auction?id=${ad.id}' />"
															style="background-image: url(${ad.pictures[0].filePath})">
														</a>
													</div>
												</div>

												<div class="tile tile-half">
													<div class="resultAd-text">

														<h2>
															<a class="link"
																href="<c:url value='/auction?id=${ad.id}' />"> <span
																class="fa fa-gavel" aria-hidden="true"></span>&nbsp;&nbsp;${ad.title }
															</a>
														</h2>

														<p>${ad.street}</p>
														<p>${ad.zipcode} ${ad.city}</p>
														<p>${ad.roomType}</p>


														<h3>CHF ${ad.prize}</h3>

														<fmt:formatDate value="${ad.moveInDate}"
															var="formattedMoveInDate" type="date"
															pattern="dd.MM.yyyy" />

														<p>Move-in date: ${formattedMoveInDate }</p>
														<p>Auction end-date: ${ad.endTime}</p>
													</div>
												</div>
											</div>

										</li>
										<%-- <li
												class="resultAd"
												data-price="${ad.prize}"
												data-moveIn="${ad.moveInDate}"
												data-age="${ad.moveInDate}">

												<a
													class="list-image-link"
													href="<c:url value='/auction?id=${ad.id}' />"
													style="background-image: url(${ad.pictures[0].filePath})">
												</a>

												<div class="resultAd-text">
													<h2>
														<a class="link" href="<c:url value='/auction?id=${ad.id}' />">
															${ad.title }
														</a>
													</h2>

													<p>${ad.street},${ad.zipcode} ${ad.city}</p>
													<p>${ad.roomType}</p>


													<h3>CHF ${ad.prize}</h3>

													<fmt:formatDate value="${ad.moveInDate}"
														var="formattedMoveInDate" type="date" pattern="dd.MM.yyyy" />

													<p>Move-in date: ${formattedMoveInDate }</p>
													<p>Auction end-date: ${ad.endTime}</p>
												</div>

											</li> --%>
									</c:otherwise>

								</c:choose>
							</c:forEach>

						</ul>

					</c:otherwise>
				</c:choose>

			</div>
			<%-- .container-scroll END --%>

		</div>
		<%-- .span-half END --%>

		</div> <%-- .row END --%>
	</div> <%-- .containerEND --%>

</main>


<%-- <c:import url="template/~footer.jsp" /> --%>
<c:import url="template/~bottom.jsp">
	<c:param name="js" value="results" />
	<c:param name="map" value="1" />
</c:import>
