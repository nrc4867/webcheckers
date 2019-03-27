package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

/**
 * Unit test for Get SigninRoute
 * @author Abhaya Tamrakar
 */
@Tag("UI-Tier")
public class GetSignInRouteTest {

    private GetSignInRoute gsir;
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;



    @BeforeEach
    public void setup(){
        request=mock(Request.class);
        session=mock(Session.class);
        when(request.session()).thenReturn(session);
        response=mock(Response.class);
        engine = mock(TemplateEngine.class);

        gsir= new GetSignInRoute(engine);
    }


    @Test
    public void handleTest(){

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        gsir.handle(request,response);

        // Analyze the content passed into the render method
        //   * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(GetSignInRoute.TITLE_ATTR, GetSignInRoute.TITLE);
        testHelper.assertViewModelAttribute(GetSignInRoute.MESSAGE_ATTR, GetSignInRoute.SIGNIN_MSG);
        //   * test view name
        testHelper.assertViewName(GetSignInRoute.VIEW_FORM);


    }

}
