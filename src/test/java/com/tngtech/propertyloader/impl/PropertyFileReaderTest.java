package com.tngtech.propertyloader.impl;

import com.tngtech.propertyloader.impl.interfaces.PropertyLoaderOpener;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PropertyFileReaderTest {

    @Mock
    private PropertyLoaderOpener propertyLoaderOpener;
    @Mock
    private PropertyLoaderFactory propertyLoaderFactory;
    @Mock
    InputStream stream;
    @Mock
    InputStreamReader inputStreamReader;
    @Mock
    Properties properties;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testTryToReadPropertiesFromFile() throws Exception {
        PropertyFileReader reader = new PropertyFileReader(propertyLoaderFactory);

        when(propertyLoaderOpener.open("testForIncludesAndVariableResolving.properties")).thenReturn(stream);
        when(propertyLoaderFactory.getEmptyProperties()).thenReturn(properties);
        when(propertyLoaderFactory.getInputStreamReader(stream,"ISO-8859-1")).thenReturn(inputStreamReader);
        doNothing().when(properties).load(inputStreamReader);

        assertEquals(properties, reader.tryToReadPropertiesFromFile("testForIncludesAndVariableResolving.properties", "ISO-8859-1", propertyLoaderOpener));

        verify(propertyLoaderOpener).open("testForIncludesAndVariableResolving.properties");
        verify(propertyLoaderFactory).getEmptyProperties();
        verify(propertyLoaderFactory).getInputStreamReader(stream, "ISO-8859-1");
        verify(properties).load(inputStreamReader);
    }
}