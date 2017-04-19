package ua.com.kurai.myapplication;

import org.junit.Before;
import org.mockito.MockitoAnnotations;


/**
 * Created by slava on 14.04.17.
 */

public class BaseTest {
    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }
}
