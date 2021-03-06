/**
 * The MIT License
 *
 * Copyright (C) 2012 Asterios Raptis
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *  *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *  *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package de.alpharogroup.resourcebundle.properties;

import static org.testng.AssertJUnit.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.testng.annotations.Test;

import de.alpharogroup.file.search.PathFinder;
import de.alpharogroup.lang.ClassExtensions;
import de.alpharogroup.lang.PackageExtensions;

public class PropertiesFileExtensionsTest
{

	@Test(enabled = true)
	public void testGetRedundantKeys() throws IOException
	{
		final File srcTestResourceDir = PathFinder.getSrcTestResourcesDir();
		final File testDir = PathFinder.getRelativePath(srcTestResourceDir, "resources",
			"properties");
		final Map<File, Map<String, List<String>>> fileMap = PropertiesFileExtensions
			.getRedundantKeys(testDir);
		for (final Map.Entry<File, Map<String, List<String>>> entry : fileMap.entrySet())
		{
			final File file = entry.getKey();
			System.out.println(file);
		}
		final File expectedPropertiesFile1 = PathFinder.getRelativePath(testDir, "test.properties");
		final File expectedPropertiesFile2 = PathFinder.getRelativePath(testDir, "foo",
			"resources.properties");
		assertTrue(fileMap.containsKey(expectedPropertiesFile1));
		assertTrue(fileMap.containsKey(expectedPropertiesFile2));
	}

	@Test(enabled = true)
	public void testLoadPropertiesFromClassObject() throws IOException
	{
		final Locale en = Locale.ENGLISH;
		Properties properties = PropertiesFileExtensions
			.loadPropertiesFromClassObject(this.getClass(), en);
		assertTrue("", properties.get("test").equals("foo"));
		properties = PropertiesFileExtensions.loadPropertiesFromClassObject(this.getClass(), null);
		assertTrue("", properties.get("test").equals("bar"));
	}


	@Test(enabled = true)
	public void testLoadPropertiesObjectPropertiesFilename() throws IOException
	{
		final String propertiesFilename = ClassExtensions.getSimpleName(getClass()) + ".properties";
		final Properties prop = PropertiesFileExtensions.loadProperties(this, propertiesFilename);
		final boolean result = null != prop;
		assertTrue("", result);
	}

	@Test(enabled = true)
	public void testLoadPropertiesPackagePath() throws IOException
	{
		final String propertiesFilename = "resources.properties";
		final String pathFromObject = PackageExtensions.getPackagePathWithSlash(this);
		final String path = pathFromObject + propertiesFilename;

		final Properties prop = PropertiesFileExtensions.loadProperties(path);
		final boolean result = null != prop;
		assertTrue("", result);
	}

	@Test(enabled = true)
	public void testLoadPropertiesPackagePathPropertiesFilename() throws IOException
	{
		String packagePath = "de/alpharogroup/lang/";
		String propertiesFilename = "resources.properties";
		Properties prop = PropertiesFileExtensions.loadProperties(packagePath, propertiesFilename);
		boolean result = null != prop;
		assertTrue("", result);

		packagePath = "/de/alpharogroup/lang//";
		propertiesFilename = "//resources.properties";
		prop = PropertiesFileExtensions.loadProperties(packagePath, propertiesFilename);
		result = null != prop;
		assertTrue("", result);

	}

	@Test(enabled = true)
	public void testLoadPropertiesPropertiesFilename() throws IOException
	{
		final String propertiesFilename = "de/alpharogroup/lang/resources.properties";
		final Properties prop = PropertiesFileExtensions.loadProperties(propertiesFilename);
		final boolean result = null != prop;
		assertTrue("", result);
	}


	@Test(enabled = true)
	public void testRemoveComments() throws IOException
	{
		final File srcTestResourceDir = PathFinder.getSrcTestResourcesDir();
		final File testDir = PathFinder.getRelativePath(srcTestResourceDir, "resources",
			"properties");
		final File propertiesFile = PathFinder.getRelativePath(testDir, "test.properties");
		final List<String> lines = PropertiesFileExtensions.removeComments(propertiesFile);
		assertTrue(lines.size() == 5);
	}

	@Test(enabled = false)
	public void testToXmlFileFileStringString()
		throws URISyntaxException, FileNotFoundException, IOException
	{
		final String propertiesFilename = "de/alpharogroup/lang/resources.properties";
		final File propertiesFile = ClassExtensions.getResourceAsFile(propertiesFilename);
		final File xmlFile = new File(propertiesFile.getParent(), "resources.properties.xml");
		PropertiesFileExtensions.toXml(propertiesFile, xmlFile, "", "UTF-8");
	}

}
