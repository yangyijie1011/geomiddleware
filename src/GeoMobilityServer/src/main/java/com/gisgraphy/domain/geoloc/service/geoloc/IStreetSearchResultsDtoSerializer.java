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
package com.gisgraphy.domain.geoloc.service.geoloc;

import java.io.OutputStream;
import java.util.Map;

import com.gisgraphy.domain.valueobject.StreetSearchResultsDto;
import com.gisgraphy.serializer.OutputFormat;

public interface IStreetSearchResultsDtoSerializer {


    /**
     * @param outputStream the {@link OutputStream} to serialize in
     * @param outputFormat the outputFormat we'd like to serialize the geolocResultsDto
     * @param streetSearchResultsDto the geolocResultsDto to serialize
     * @param indent whether the stream should be indented (if the format support indentation)
     *  @param extraParameters a map that contains named parameters (callback method, startpaginationindex, needed for RSS and ATOM)
     * the parameters can be use for a certain format but not for all)
     */
    public abstract void serialize(OutputStream outputStream,OutputFormat outputFormat,
	    StreetSearchResultsDto streetSearchResultsDto,boolean indent,Map<String,Object> extraParameters);

}