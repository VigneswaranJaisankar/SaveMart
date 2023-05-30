package com.scripted.generic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class FileUtils {
	private static final Logger log = LogManager.getLogger(FileUtils.class);
	private static String cdir = System.getProperty("user.dir");

	public static String getFilePath(String fileName) throws Exceptions {

		log.debug("Inside FileUtil.getFilePath method");
		String filePath = cdir + File.separator + fileName;
		try {
			File file = new File(filePath);
		} catch (Exception e) {
			log.error("Error while trying to get file path" + "Exception " + e);
			Assert.fail("Error while trying to get file path" + "Exception " + e);

		}
		return cdir + File.separator + fileName;
	}

	public static void setDirectory(String path) {
		log.info("Inside FileUtil.setDirectory method");
		cdir = path;
		try {
			File file = new File(cdir);
			if (!file.exists())
				throw new Exceptions(path + " - Path not found");

		} catch (Exception e) {
			log.error("Error while trying to set directory" + "Exception " + e);
			Assert.fail("Error while trying to set directory" + "Exception " + e);

		}
	}

	public static String getCurrentDir() throws Exceptions {
		return cdir;
	}

	public static File createTempDirectory(String folderName) {
		log.info("Inside FileUtil.createTempDirectory method");
		File tempDir = new File(System.getProperty("java.io.tmpdir", null), folderName);
		if (!tempDir.exists())
			tempDir.mkdir();
		FileUtils.waitforFile(tempDir.getAbsolutePath(), 10000);
		return tempDir;
	}

	public static boolean fileCopy(String targetPath, String destPath) {
		log.info("Inside FileUtils.fileCopy method");
		File inputFile = new File(targetPath);
		File outputFile = new File(destPath);
		try (FileReader in = new FileReader(inputFile); FileWriter out = new FileWriter(outputFile)) {
			int line;
			while ((line = in.read()) != -1) {
				out.write(line);
			}
			in.close();
			out.close();
		} catch (Exception e) {
			log.error("Error while copying file" + "Exception" + e);
			Assert.fail("Error while copying file" + "Exception" + e);
			return false;

		}
		return true;
	}

	public static void moveFile(String source, String dest) throws IOException {
		File afile = new File(source);
		File bfile = new File(dest);
		try (InputStream inStream = new FileInputStream(afile); OutputStream outStream = new FileOutputStream(bfile)) {
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, length);
			}
			outStream.flush();
			inStream.close();
			outStream.close();
			boolean file = afile.delete();
			if (!file) {
				log.error("Failed to delete the file");
			}
		} catch (Exception e) {
			log.error("Error while moving file" + "Exception" + e);
			Assert.fail("Error while moving file" + "Exception" + e);

		}
	}

	public static boolean jarCopy(String targetPath, String destPath) {
		try (FileChannel in = new FileInputStream(targetPath).getChannel();
				FileChannel out = new FileOutputStream(destPath).getChannel();) {
			in.transferTo(0, in.size(), out);
			in.close();
			out.close();
		} catch (Exception e) {
			log.error("Error while copying jar" + "Exception" + e);
			Assert.fail("Error while copying jar" + "Exception" + e);
			return false;
		}
		return true;
	}

	public static String makeDirs(String path) throws NullPointerException {
		File newFile = null;
		String abspath ="";
		try {
			String pattern = Pattern.quote(System.getProperty("file.separator"));
			String[] folders = new File(path).getAbsolutePath().split(pattern);
			String filePath = "";
			if (newFile == null) {
				for (String folder : folders) {
					filePath = filePath + System.getProperty("file.separator") + folder;
					newFile = new File(filePath);
					if (!newFile.exists() && !folder.contains(":") && !folder.contains(".")) {
						newFile.mkdir();
						FileUtils.waitforFile(newFile.getAbsolutePath(), 10000);
						if (abspath == null || abspath.isEmpty()) {
						abspath = newFile.getAbsolutePath();
						}
						if (!newFile.isDirectory()) {
							log.info("Unable to create parent directories of " + filePath);
							Assert.fail("Unable to create parent directories of " + filePath);
							throw new RuntimeException("Unable to create parent directories of " + filePath);

						}
						log.info("Created : " + newFile.getAbsolutePath());
					}
				}
			}
		} catch (Exception e) {
			log.error("Error while making directory" + "Exception" + e);
			Assert.fail("Error while making directory" + "Exception" + e);
		}
		return abspath ;
	}

	public static boolean createSimpleFile(String path, String body) {
		log.info("Inside FileUtils.createSimpleFile method");
		Boolean filestatus = false;
		File tempFile = new File(path);
		if (tempFile.exists()) {
			filestatus = tempFile.delete();
		}
		if (filestatus) {
			log.debug("file deleted");
		}
		try (BufferedWriter out = new BufferedWriter(new FileWriter(path))) {
			out.write(body);
			out.close();
		} catch (IOException e) {
			log.error("Error while trying to create simple file" + "Exception" + e);
			Assert.fail("Error while trying to create simple file" + "Exception" + e);
			return false;
		}
		return true;
	}

	public static void deleteFile(String path) {
		log.info("Inside FileUtils.delete method");
		Boolean filestatus = false;
		File file = new File(path);
		try {
			if (file.exists()) {
				filestatus = file.delete();
			}
			FileUtils.waitforFileDelete(file.getAbsolutePath(), 10000);
			if (filestatus) {
				log.debug("file deleted");
			}
		} catch (Exception e) {
			log.error("Error while deleting file" + "Exception :" + e);
			Assert.fail("Error while deleting file" + "Exception :" + e);
		}
	}

	public static FilenameFilter getFileExtensionFilter(String extension) {
		log.info("Inside FileUtils.getFileExtensionFilter method");
		final String _extension = extension;
		return new FilenameFilter() {
			public boolean accept(File file, String name) {
				boolean ret = name.endsWith(_extension);
				return ret;
			}
		};
	}

	public static void createPropertiesFile(String destPath, Map<String, String> data) throws IOException {
		log.info("Inside FileUtils.createPropertiesFile method");
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(destPath)))) {
			Iterator<String> mapIterator = data.keySet().iterator();
			while (mapIterator.hasNext()) {
				String key = mapIterator.next();
				Object value = data.get(key);
				bw.write(key);
				bw.write(" = ");
				bw.write(value.toString());
				bw.newLine();
			}
			bw.close();
		} catch (Exception e) {
			log.error("Error while creating property file" + "Exception :" + e);
			Assert.fail("Error while creating property file" + "Exception :" + e);
		}
	}

	public static void deleteDirectory(File path) {
		log.debug("Inside FileUtils.deleteDirectory method");
		try {
			if (path.exists()) {
				File[] files = path.listFiles();
				for (int i = 0; i < files.length; i++) {
					if (files[i].isDirectory()) {
						deleteDirectory(files[i]);
					} else {
						boolean filestatus =files[i].delete();
						if(filestatus) {
							log.debug("file deleted");
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Error occurred while deleting directory" + "Exception :" + e);
			Assert.fail("Error occurred while deleting directory" + "Exception :" + e);
		}

		// path.delete();
		// FileUtils.waitforFileDelete(path.getAbsolutePath(), 10000);
	}

	public static String getFileToString(File file) throws IOException {
		log.info("Inside FileUtils.getFileToString method");
		FileReader fileReader = new FileReader(file);
		StringWriter output = new StringWriter();
		try {
			IOUtils.copy(fileReader, output);
		} catch (Exception e) {
			log.error("Error while copying file" + "Exception :" + e);
			Assert.fail("Error while copying file" + "Exception :" + e);
		} finally {
			fileReader.close();
		}
		String string = output.toString();
		return string;

	}

	public static String getTimeStampFolderPath(String root) throws IOException {
		log.info("Inside FileUtils.getTimeStampFolderPath method");
		File file = new File(root);
		try {
			String path = file.getAbsolutePath() + File.separator + DateTimeHelper.getTimeStamp();
			makeDirs(path);
			FileUtils.waitforFile(path, 10000);
			file = new File(path);
		} catch (Exception e) {
			log.error("Error while getting timestamp folder path" + "Exception :" + e);
			Assert.fail("Error while getting timestamp folder path" + "Exception :" + e);
		}
		return file.getAbsolutePath();

	}

	@SuppressWarnings("unused")
	public static String createTempFilePath(String folder, String file) throws IOException, NullPointerException {
		File image = null;
		String abspath ="";
		try {
			final File temp;
			String tempDir = System.getProperty("java.io.tmpdir");
			String path = tempDir + File.separator + folder;
			makeDirs(path);
			temp = new File(path);
			if (!(temp.mkdir())) {
				Assert.fail("Error while creating temperory directory ");
				throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());

			}
			String imagePath = path + File.separator + file;
			image = new File(imagePath);
			abspath= image.getAbsolutePath();
			if (image == null) {
				throw new NullPointerException("imagePath is null here");
			}
			boolean imagestatus = false;
			if (image.exists() && image != null) {
				imagestatus = image.delete();
			}			
			if(imagestatus) {
				log.info("image deleted");	
			}
		} catch (Exception e) {
			log.error("Error while creating temperory file" + "Exception :" + e);
			Assert.fail("Error while creating temperory file" + "Exception :" + e);
		}
		return abspath;
	}

	public static void waitforFileDelete(String filename, long milliseconds) {
		long val = 0;
		File file = new File(filename);
		while (file.exists()) {
			if (val >= milliseconds)
				throw new RuntimeException("File : " + filename + " Not Found.");
			try {
				Thread.sleep(100);
				val = val + 100;
			} catch (InterruptedException ie) {
				log.error("Error occured while waiting for file delete" + "Exception :" + ie);
				Assert.fail("Error occured while waiting for file delete" + "Exception :" + ie);
				Thread.currentThread().interrupt();
			}
		}
	}

	public static void waitforFile(String filename, long milliseconds) {
		long val = 0;
		File file = new File(filename);
		while (!file.exists()) {
			if (val >= milliseconds)
				throw new RuntimeException("File : " + filename + " Not Found.");
			try {
				Thread.sleep(100);
				val = val + 100;
			} catch (InterruptedException ie) {
				log.error("Error while waiting for file " + "Exception :" + ie);
				Assert.fail("Error while waiting for file " + "Exception :" + ie);
				Thread.currentThread().interrupt();
			}
		}
	}

	private static String getCurrentDirectory() {
		Properties prop = new Properties();
		try {
			File file = null;
			try {
				file = getJarDir(FileUtils.class);
				cdir = file.getParentFile().getAbsolutePath();
				file = new File(cdir);
			} catch (Exception e) {
				cdir = System.getProperty("user.dir");
				file = new File(cdir);
			}
		} catch (Exception e) {
			log.error("Error in connect : " + e.getMessage());
			Assert.fail("Error in connect : " + e.getMessage());
			throw new RuntimeException(e);
		}
		return cdir;
	}

	static public void copyResourcesToTemp(String root, String resourcePath) throws Exception {
		File rootFile = createTempDirectory(root);
		String outPath = rootFile + resourcePath;
		String dir = makeDirs(outPath);
		InputStream stream = null;
		String jarFolder;
		try (OutputStream resStreamOut = new FileOutputStream(outPath)) {
			stream = FileUtils.class.getResourceAsStream(resourcePath);// note that each / is a directory down in the
																		// "jar tree" been the jar the root of the tree
			if (stream == null) {
				throw new Exception("Cannot get resource \"" + resourcePath + "\" from Jar file.");
			}

			int readBytes;
			byte[] buffer = new byte[4096];
			jarFolder = new File(FileUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath())
					.getParentFile().getPath().replace('/', '/');

			while ((readBytes = stream.read(buffer)) > 0) {
				resStreamOut.write(buffer, 0, readBytes);
			}
		} catch (Exception ex) {
			log.error("Error while copying resources to Temp " + "Exception :" + ex);
			Assert.fail("Error while copying resources to Temp " + "Exception :" + ex);
			throw ex;

		} finally {
			if (stream != null)
				stream.close();
		}

	}

	public static File getJarDir(Class aclass) {
		URL url;
		String extURL; // url.toExternalForm();
		// get an url
		try {
			url = aclass.getProtectionDomain().getCodeSource().getLocation();
		} catch (SecurityException ex) {
			url = aclass.getResource(aclass.getSimpleName() + ".class");
		}

		// convert to external form
		extURL = url.toExternalForm();

		// prune for various cases
		if (extURL.endsWith(".jar")) // from getCodeSource
			extURL = extURL.substring(0, extURL.lastIndexOf(File.separator));
		else { // from getResource
			String suffix = File.separator + (aclass.getName()).replace(".", File.separator) + ".class";
			extURL = extURL.replace(suffix, "");
			if (extURL.startsWith("jar:") && extURL.endsWith(".jar!"))
				extURL = extURL.substring(4, extURL.lastIndexOf(File.separator));
		}

		// convert back to url
		try {
			url = new URL(extURL);
		} catch (MalformedURLException mux) {
			// leave url unchanged; probably does not happen
		}

		// convert url to File
		try {
			return new File(url.toURI());
		} catch (URISyntaxException ex) {
			return new File(url.getPath());
		}
	}
}
