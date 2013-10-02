/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.aerogear.rest.service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.aerogear.rest.model.Member;
import org.jboss.aerogear.rest.persistence.PersistenceSimulator;

/**
 * 
 * Used just to return some valid responses. Preflight response needs only Access-Control-Allow-Origin but this is a dummy WS.
 * 
 */
@Path("/memberservice")
public class MemberService {

    private String _corsHeaders;

    private Response makeCORS(ResponseBuilder rb, String returnMethod) {

        rb.header("Access-Control-Allow-Origin", "*")
          .header("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE")
          .header("Access-Control-Expose-Headers", "accept,origin,content-type,link,ag-links-next,ag-links-previous,Link,next,previous,next_page,previous_page")
          .header("Access-Control-Allow-Credentials", "true");

        if (!"".equals(returnMethod) && returnMethod != null) {
            rb.header("Access-Control-Allow-Headers", returnMethod);
        }

        return rb.build();
    }

    private Response makeCORS(ResponseBuilder req) {
        return makeCORS(req, _corsHeaders);
    }

    @GET
    @Path("/membersjsonp")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMembersJSONP(@QueryParam("limit") String limit, @QueryParam("description") String description) {

        Response.ResponseBuilder builder = null;
        try {
            builder = Response.ok(PersistenceSimulator.getMembers(limit, description));
        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }
        return builder.build();
    }

    @OPTIONS
    @Path("/members")
    public Response corsGetMembers(@HeaderParam("Access-Control-Request-Headers") String requestH) {
        _corsHeaders = requestH;
        return makeCORS(Response.ok(), requestH);
    }

    @GET
    @Path("/members")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMembers(@QueryParam("limit") String limit, @QueryParam("description") String description) {

        Response.ResponseBuilder builder = null;
        try {
            builder = Response.ok(PersistenceSimulator.getMembersByLimitAndDesc(limit, description));
        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }
        return makeCORS(builder);
    }

    @OPTIONS
    @Path("/member/{id}")
    public Response corsGetMember(@HeaderParam("Access-Control-Request-Headers") String requestH, @PathParam("id") String id) {
        _corsHeaders = requestH;
        return makeCORS(Response.ok(), requestH);
    }

    @GET
    @Path("/member/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMember(@QueryParam("limit") String limit, @PathParam("id") String id) {

        Response.ResponseBuilder builder = null;
        try {
            builder = Response.ok(PersistenceSimulator.getMembers(limit, id));
        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }
        return makeCORS(builder);
    }

    @OPTIONS
    @Path("/memberpages")
    public Response corsGetMemberPages(@HeaderParam("Access-Control-Request-Headers") String requestH) {
        _corsHeaders = requestH;
        return makeCORS(Response.ok(), requestH);
    }

    @GET
    @Path("/memberpages")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMemberPages(@QueryParam("limit") String limit, @QueryParam("offset") String offset) {

        Response.ResponseBuilder builder = null;
        try {
            builder = Response.ok(PersistenceSimulator.getMembers(limit, ""));
            builder.header("Access-Control-Expose-Headers", "Link");
            builder.header(
                    "Link",
                    "<http://127.0.0.1:8080/aerogear-rest-service/rest/memberservice/memberpages?offset=0&limit=2>; rel=\"previous\",<http://127.0.0.1:8080/aerogear-rest-service/rest/memberservice/memberpages?offset=2&limit=2>; rel=\"next\"");

        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }
        return makeCORS(builder);
    }

    @OPTIONS
    @Path("/memberpagescustom")
    public Response corsGetMemberPagesCustomId(@HeaderParam("Access-Control-Request-Headers") String requestH) {
        _corsHeaders = requestH;
        return makeCORS(Response.ok(), requestH);
    }

    @GET
    @Path("/memberpagescustom")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMemberPagesCustomId(@QueryParam("limit") String limit, @QueryParam("offset") String offset) {

        Response.ResponseBuilder builder = null;
        try {
            builder = Response.ok(PersistenceSimulator.getMembers(limit, ""));
            builder.header("Access-Control-Expose-Headers", "Link");
            builder.header(
                    "Link",
                    "<http://127.0.0.1:8080/aerogear-rest-service/rest/memberservice/memberpages?offset=0&limit=2>; rel=\"previous_page\",<http://127.0.0.1:8080/aerogear-rest-service/rest/memberservice/memberpages?offset=2&limit=2>; rel=\"next_page\"");

        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }
        return makeCORS(builder);
    }

    @OPTIONS
    @Path("/memberpagescustomparams")
    public Response corsGetMemberPagesCustomParams(@HeaderParam("Access-Control-Request-Headers") String requestH) {
        _corsHeaders = requestH;
        return makeCORS(Response.ok(), requestH);
    }

    @GET
    @Path("/memberpagescustomparams")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMemberPagesCustomParams(@QueryParam("pageNumber") String limit, @QueryParam("objectLimit") String offset) {

        Response.ResponseBuilder builder = null;
        try {
            builder = Response.ok(PersistenceSimulator.getMembers(limit, ""));
            builder.header("Access-Control-Expose-Headers", "Link");
            builder.header(
                    "Link",
                    "<http://127.0.0.1:8080/aerogear-rest-service/rest/memberservice/memberpages?pageNumber=0&objectLimit=2>; rel=\"previous\",<http://127.0.0.1:8080/aerogear-rest-service/rest/memberservice/memberpages?pageNumber=2&objectLimit=2>; rel=\"next\"");

        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }
        return makeCORS(builder);
    }

    @OPTIONS
    @Path("/memberpagescustomidsandparams")
    public Response corsGetMemberPagesCustomIdsAndParams(@HeaderParam("Access-Control-Request-Headers") String requestH) {
        _corsHeaders = requestH;
        return makeCORS(Response.ok(), requestH);
    }

    @GET
    @Path("/memberpagescustomidsandparams")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMemberPagesCustomIdsAndParams(@QueryParam("pageNumber") String limit,
            @QueryParam("objectLimit") String offset) {

        Response.ResponseBuilder builder = null;
        try {
            builder = Response.ok(PersistenceSimulator.getMembers(limit, ""));
            builder.header("Access-Control-Expose-Headers", "Link");
            builder.header(
                    "Link",
                    "<http://127.0.0.1:8080/aerogear-rest-service/rest/memberservice/memberpages?pageNumber=0&objectLimit=2>; rel=\"previous_page\",<http://127.0.0.1:8080/aerogear-rest-service/rest/memberservice/memberpages?pageNumber=2&objectLimit=2>; rel=\"next_page\"");

        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }
        return makeCORS(builder);
    }

    @OPTIONS
    @Path("/memberpages/headertest")
    public Response corsGetMemberPagesHeader(@HeaderParam("Access-Control-Request-Headers") String requestH) {
        _corsHeaders = requestH;
        return makeCORS(Response.ok(), requestH);
    }

    @GET
    @Path("/memberpages/headertest")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMemberPagesHeader(@QueryParam("offset") String limit, @QueryParam("limit") String id) {

        Response.ResponseBuilder builder = null;
        try {
            builder = Response.ok(PersistenceSimulator.getMembers(limit, ""));
            builder.header("Access-Control-Expose-Headers", "next,previous");
            builder.header("next", "/memberpages/headertest?offset=2&limit=2");
            builder.header("previous", "/memberpages/headertest?offset=0&limit=2");

        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }
        return makeCORS(builder);
    }

    @OPTIONS
    @Path("/memberpages/headertestcustomids")
    public Response corsGetMemberPagesHeaderCustomIds(@HeaderParam("Access-Control-Request-Headers") String requestH) {
        _corsHeaders = requestH;
        return makeCORS(Response.ok(), requestH);
    }

    @GET
    @Path("/memberpages/headertestcustomids")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMemberPagesHeaderCustomIds(@QueryParam("offset") String limit, @QueryParam("limit") String id) {

        Response.ResponseBuilder builder = null;
        try {
            builder = Response.ok(PersistenceSimulator.getMembers(limit, ""));
            builder.header("Access-Control-Expose-Headers", "next_page,previous_page");
            builder.header("next_page", "/memberpages/headertestcustomids?offset=2&limit=2");
            builder.header("previous_page", "/memberpages/headertestcustomids?offset=0&limit=2");

        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }
        return makeCORS(builder);
    }

    @OPTIONS
    @Path("/memberpages/headertestcustomparams")
    public Response corsGetMemberPagesHeaderCustomParams(@HeaderParam("Access-Control-Request-Headers") String requestH) {
        _corsHeaders = requestH;
        return makeCORS(Response.ok(), requestH);
    }

    @GET
    @Path("/memberpages/headertestcustomparams")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMemberPagesHeaderCustomParams(@QueryParam("pageNumber") String pageNumber,
            @QueryParam("objectLimit") String limit) {

        Response.ResponseBuilder builder = null;
        try {
            builder = Response.ok(PersistenceSimulator.getMembers(limit, ""));
            builder.header("Access-Control-Expose-Headers", "next,previous");
            builder.header("next", "/memberpages/headertestcustomparams?pageNumber=2&objectLimit=2");
            builder.header("previous", "/memberpages/headertestcustomparams?pageNumber=0&objectLimit=2");

        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }
        return makeCORS(builder);
    }

    @OPTIONS
    @Path("/memberpages/headertestcustomidsandparams")
    public Response corsGetMemberPagesHeaderCustomIdsAndParams(@HeaderParam("Access-Control-Request-Headers") String requestH) {
        _corsHeaders = requestH;
        return makeCORS(Response.ok(), requestH);
    }

    @GET
    @Path("/memberpages/headertestcustomidsandparams")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMemberPagesHeaderCustomIdsAndParams(@QueryParam("pageNumber") String pageNumber,
            @QueryParam("objectLimit") String limit) {

        Response.ResponseBuilder builder = null;
        try {
            builder = Response.ok(PersistenceSimulator.getMembers(limit, ""));
            builder.header("Access-Control-Expose-Headers", "next_page,previous_page");
            builder.header("next_page", "/memberpages/headertestcustomparams?pageNumber=2&objectLimit=2");
            builder.header("previous_page", "/memberpages/headertestcustomparams?pageNumber=0&objectLimit=2");

        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }
        return makeCORS(builder);
    }

    @OPTIONS
    @Path("/memberpages/headertestcustomidsparamsandparamprovider")
    public Response corsGetMemberPagesHeaderCustomIdsParamsAndParamProvider(
            @HeaderParam("Access-Control-Request-Headers") String requestH) {
        _corsHeaders = requestH;
        return makeCORS(Response.ok(), requestH);
    }

    @GET
    @Path("/memberpages/headertestcustomidsparamsandparamprovider")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMemberPagesHeaderCustomIdsParamsAndParamProvider(@QueryParam("pageNumber") String pageNumber,
            @QueryParam("objectLimit") String limit) {

        Response.ResponseBuilder builder = null;
        try {
            builder = Response.ok(PersistenceSimulator.getMembers(limit, ""));
            builder.header("Access-Control-Expose-Headers", "next_page,previous_page");
            builder.header("next_page", "{\"pageNumber\":0,\"objectLimit\":2}");
            builder.header("previous_page", "{\"pageNumber\":2,\"objectLimit\":2}");

        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }
        return makeCORS(builder);
    }

    @OPTIONS
    @Path("/memberpages/bodytest")
    public Response corsGetMemberPagesBody(@HeaderParam("Access-Control-Request-Headers") String requestH) {
        _corsHeaders = requestH;
        return makeCORS(Response.ok(), requestH);
    }

    @GET
    @Path("/memberpages/bodytest")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMemberPagesBody(@QueryParam("limit") String limit, @QueryParam("offset") String offset) {

        Response.ResponseBuilder builder = null;
        try {
            builder = Response
                    .ok("{\"next\":{\"offset\":\"2\",\"limit\":\"2\"},\"previous\":{\"offset\":\"0\",\"limit\":\"2\"}}");

        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }
        return makeCORS(builder);
    }

    @OPTIONS
    @Path("/memberpages/bodytestcustomids")
    public Response corsGetMemberPagesBodyCustomIds(@HeaderParam("Access-Control-Request-Headers") String requestH) {
        _corsHeaders = requestH;
        return makeCORS(Response.ok(), requestH);
    }

    @GET
    @Path("/memberpages/bodytestcustomids")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMemberPagesBodyCustomIds(@QueryParam("limit") String limit, @QueryParam("offset") String offset) {

        Response.ResponseBuilder builder = null;
        try {
            builder = Response
                    .ok("{\"next_page\":{\"offset\":\"2\",\"limit\":\"2\"},\"previous_page\":{\"offset\":\"0\",\"limit\":\"2\"}}");

        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }
        return makeCORS(builder);
    }

    @OPTIONS
    @Path("/memberpages/bodytestcustomqueryparams")
    public Response corsGetMemberPagesBodyCustomQueryParams(@HeaderParam("Access-Control-Request-Headers") String requestH) {
        _corsHeaders = requestH;
        return makeCORS(Response.ok(), requestH);
    }

    @GET
    @Path("/memberpages/bodytestcustomqueryparams")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMemberPagesBodyCustomQueryParams(@QueryParam("pageNumber") String pageNumber,
            @QueryParam("objectLimit") String limit) {

        Response.ResponseBuilder builder = null;
        try {
            builder = Response
                    .ok("{\"next\":{\"offset\":\"2\",\"limit\":\"2\"},\"previous\":{\"offset\":\"0\",\"limit\":\"2\"}}");

        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }
        return makeCORS(builder);
    }

    @OPTIONS
    @Path("/memberpages/bodytestcustomidsandparams")
    public Response corsGetMemberPagesBodyCustomIdsAndParams(@HeaderParam("Access-Control-Request-Headers") String requestH) {
        _corsHeaders = requestH;
        return makeCORS(Response.ok(), requestH);
    }

    @GET
    @Path("/memberpages/bodytestcustomidsandparams")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMemberPagesBodyCustomIdsAndParams(@QueryParam("pageNumber") String pageNumber,
            @QueryParam("objectLimit") String limit) {

        Response.ResponseBuilder builder = null;
        try {
            builder = Response
                    .ok("{\"next_page\":{\"offset\":\"2\",\"limit\":\"2\"},\"previous_page\":{\"offset\":\"0\",\"limit\":\"2\"}}");

        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }
        return makeCORS(builder);
    }

    @OPTIONS
    @Path("/memberpages/bodytestcustomidsparamsandparamprovider")
    public Response corsGetMemberPagesBodyCustomIdsParamsAndParamProvider(
            @HeaderParam("Access-Control-Request-Headers") String requestH) {
        _corsHeaders = requestH;
        return makeCORS(Response.ok(), requestH);
    }

    @GET
    @Path("/memberpages/bodytestcustomidsparamsandparamprovider")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMemberPagesBodyCustomIdsParamsAndParamProvider(@QueryParam("pageNumber") String pageNumber,
            @QueryParam("objectLimit") String limit) {

        Response.ResponseBuilder builder = null;
        try {

            builder = Response
                    .ok("{ \"next_page\": { \"pageNumber\": \"1\", \"objectLimit\": \"2\"}, \"previous_page\": { \"pageNumber\": \"3\", \"objectLimit\": \"2\" }}");

        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }
        return makeCORS(builder);
    }

    @OPTIONS
    @Path("/add")
    public Response corsAddMember(@HeaderParam("Access-Control-Request-Headers") String requestH) {
        _corsHeaders = requestH;
        return makeCORS(Response.ok(), requestH);
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMember(Member member) {

        Response.ResponseBuilder builder = null;
        try {
            builder = Response.ok(PersistenceSimulator.addMember(member));
        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }
        return makeCORS(builder);
    }

    @OPTIONS
    @Path("/update/{id}")
    public Response corsUpdateMember(@HeaderParam("Access-Control-Request-Headers") String requestH, @PathParam("id") String id) {
        _corsHeaders = requestH;
        return makeCORS(Response.ok(), requestH);
    }

    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateMember(Member member, @PathParam("id") String id) {

        Response.ResponseBuilder builder = null;
        try {
            builder = Response.ok(PersistenceSimulator.updateMember(member));
        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }
        return makeCORS(builder);
    }

    @OPTIONS
    @Path("/remove/{id}")
    public Response corsremoveMember(@HeaderParam("Access-Control-Request-Headers") String requestH, @PathParam("id") String id) {
        _corsHeaders = requestH;
        return makeCORS(Response.ok(), requestH);
    }

    @DELETE
    @Path("/remove/{id}")
    public Response removeMember(@PathParam("id") String id) {

        Response.ResponseBuilder builder = null;
        try {
            PersistenceSimulator.removeMember(id);
            builder = Response.ok("[]");
        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }
        return makeCORS(builder);
    }
}
