/*******************************************************************************
 *   Gisgraphy Project 
 * 
 *   This library is free software; you can redistribute it and/or
 *   modify it under the terms of the GNU Lesser General Public
 *   License as published by the Free Software Foundation; either
 *   version 2.1 of the License, or (at your option) any later version.
 * 
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *   Lesser General Public License for more details.
 * 
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the Free Software
 *   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA
 * 
 *  Copyright 2008  Gisgraphy project 
 *  David Masclet <davidmasclet@gisgraphy.com>
 *  
 *  
 *******************************************************************************/
package com.gisgraphy.servlet;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.gisgraphy.domain.geoloc.service.ServiceException;
import com.gisgraphy.domain.geoloc.service.geoloc.GeolocErrorVisitor;
import com.gisgraphy.domain.geoloc.service.geoloc.GeolocQuery;
import com.gisgraphy.domain.geoloc.service.geoloc.GeolocQueryHttpBuilder;
import com.gisgraphy.domain.geoloc.service.geoloc.IGeolocSearchEngine;
import com.gisgraphy.domain.valueobject.Constants;
import com.gisgraphy.domain.valueobject.GisgraphyServiceType;
import com.gisgraphy.helper.HTMLHelper;
import com.gisgraphy.serializer.IoutputFormatVisitor;
import com.gisgraphy.serializer.OutputFormat;

/**
 * Provides a servlet Wrapper around The Gisgraphy geoloc Service, it Maps web
 * parameters to create a {@linkplain GeolocQuery}
 * 
 * @author <a href="mailto:david.masclet@gisgraphy.com">David Masclet</a>
 */
public class GeolocServlet extends GisgraphyServlet {

	public static final String PLACETYPE_PARAMETER = "placetype";
	public static final String LAT_PARAMETER = "lat";
	public static final String LONG_PARAMETER = "lng";
	public static final String RADIUS_PARAMETER = "radius";
	public static final String DISTANCE_PARAMETER = "distance";
	public static final String CALLBACK_PARAMETER = "callback";
	public static final int DEFAULT_MAX_RESULTS = 10;

	private boolean debugMode = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		try {
			WebApplicationContext springContext = WebApplicationContextUtils
					.getWebApplicationContext(getServletContext());
			geolocSearchEngine = (IGeolocSearchEngine) springContext
					.getBean("geolocSearchEngine");
			logger
					.info("geolocSearchEngine is injected :"
							+ geolocSearchEngine);
			this.debugMode = Boolean.valueOf(getInitParameter("debugMode"));
			logger.info("GeolocServlet debugmode = " + this.debugMode);
		} catch (Exception e) {
			logger.error("Can not start GeolocServlet : " + e.getMessage());
		}
	}

	/**
	 * Default serialVersionUID
	 */
	private static final long serialVersionUID = -9054548241743095743L;

	/**
	 * The logger
	 */
	protected static final Logger logger = LoggerFactory
			.getLogger(GeolocServlet.class);

	private IGeolocSearchEngine geolocSearchEngine;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		OutputFormat format = OutputFormat.getDefault();
		try {
			format = setResponseContentType(req, resp);
			// check empty query
			if (HTMLHelper
					.isParametersEmpty(req, LAT_PARAMETER, LONG_PARAMETER)) {
				sendCustomError(ResourceBundle.getBundle(
						Constants.BUNDLE_ERROR_KEY).getString(
						"error.emptyLatLong"), format, resp, req);
				return;
			}
			GeolocQuery query = GeolocQueryHttpBuilder.getInstance().buildFromHttpRequest(req);
			if (logger.isDebugEnabled()) {
				logger.debug("query=" + query);
				logger.debug("fulltext engine=" + geolocSearchEngine);
			}
			String UA = req.getHeader("User-Agent");
			String referer = req.getHeader("Referer");
			if (logger.isInfoEnabled()) {
				logger.info("A geoloc request from " + req.getRemoteHost()
						+ " / " + req.getRemoteAddr()
						+ " was received , Referer : " + referer + " , UA : "
						+ UA);
			}

			try {
				geolocSearchEngine.executeAndSerialize(query, resp
						.getOutputStream());
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			logger.error("error while execute a geoloc query from http request : "
							+ e);
			String errorMessage = this.debugMode ? " : " + e.getMessage() : "";
			sendCustomError(ResourceBundle
					.getBundle(Constants.BUNDLE_ERROR_KEY).getString(
							"error.error")
					+ errorMessage, format, resp, req);
			return;
		}

	}

	/**
	 * @param geolocSearchEngine
	 *            the geolocSearchEngine to set
	 */
	public void setGeolocSearchEngine(IGeolocSearchEngine geolocSearchEngine) {
		this.geolocSearchEngine = geolocSearchEngine;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gisgraphy.servlet.GisgraphyServlet#getGisgraphyServiceType()
	 */
	@Override
	public GisgraphyServiceType getGisgraphyServiceType() {
		return GisgraphyServiceType.GEOLOC;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gisgraphy.servlet.GisgraphyServlet#getErrorVisitor(java.lang.String)
	 */
	@Override
	public IoutputFormatVisitor getErrorVisitor(String errorMessage) {
		return new GeolocErrorVisitor(errorMessage);
	}

}
