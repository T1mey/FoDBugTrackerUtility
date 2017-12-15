/*******************************************************************************
 * (c) Copyright 2017 Hewlett Packard Enterprise Development LP
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the Software"),
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the 
 * Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included 
 * in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE 
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR 
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN 
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS 
 * IN THE SOFTWARE.
 ******************************************************************************/
package com.fortify.processrunner;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

import org.junit.Test;
import org.springframework.context.support.GenericApplicationContext;

import com.fortify.api.util.spring.SpringContextUtil;

/**
 * This class tests whether the available configuration files can be successfully loaded by Spring.
 * @author Ruud Senden
 *
 */
public class ConfigFilesTest {
	@Test
	public void testConfigFiles() {
		String[] files = new File("processrunner-config").list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".xml");
			}
		});
		for ( String file : files ) {
			GenericApplicationContext ctx = SpringContextUtil.loadApplicationContextFromFiles(true, "processrunner-config/"+file);
			System.out.println(ctx+": "+Arrays.asList(ctx.getBeanDefinitionNames()));
		}
	}
}
