package gruppo01.clients;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
public class saveFile {

	/** The path to the folder where we want to store the uploaded files */
	private static final String UPLOAD_FOLDER = "c:/uploadedFiles/";
	public saveFile() {
	}
	@Context
	private UriInfo context;
	/**
	 * Returns text response to caller containing uploaded file location
	 * 
	 * @return error response in case of missing parameters an internal
	 *         exception or success response if file has been stored
	 *         successfully
	 */
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public static Response uploadFile(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {
		// check if all form parameters are provided
		if (uploadedInputStream == null || fileDetail == null)
			return Response.status(400).entity("Invalid form data").build();
		// create our destination folder, if it not exists
		try {
			createFolderIfNotExists(UPLOAD_FOLDER);
		} catch (SecurityException se) {
			return Response.status(500)
					.entity("Can not create destination folder on server")
					.build();
		}
		String uploadedFileLocation = UPLOAD_FOLDER + fileDetail.getFileName();
		try {
			saveToFile(uploadedInputStream, uploadedFileLocation);
		} catch (IOException e) {
			return Response.status(500).entity("Can not save file").build();
		}
		return Response.status(200)
				.entity("File saved to " + uploadedFileLocation).build();
	}
	/**
	 * Utility method to save InputStream data to target location/file
	 * 
	 * @param inStream
	 *            - InputStream to be saved
	 * @param target
	 *            - full path to destination file
	 */
	private static void saveToFile(InputStream inStream, String target)
			throws IOException {
		OutputStream out = null;
		int read = 0;
		byte[] bytes = new byte[1024];
		out = new FileOutputStream(new File(target));
		while ((read = inStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();
	}
	/**
	 * Creates a folder to desired location if it not already exists
	 * 
	 * @param dirName
	 *            - full path to the folder
	 * @throws SecurityException
	 *             - in case you don't have permission to create the folder
	 */
	private static void createFolderIfNotExists(String dirName)
			throws SecurityException {
		File theDir = new File(dirName);
		if (!theDir.exists()) {
			theDir.mkdir();
		}
	}
	
//	public void uploadFile() {
//		File inFile = new File("C:\\Users\\admin\\Desktop\\Yana-make-up.jpg");
//		FileInputStream fis = null;
//		try {
//			fis = new FileInputStream(inFile);
//			DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
//			
//			// server back-end URL
//			HttpPost httppost = new HttpPost("http://localhost:8080/FileUploaderRESTService-1/rest/upload");
//			MultipartEntity entity = new MultipartEntity();
//			// set the file input stream and file name as arguments
//			entity.addPart("file", new InputStreamBody(fis, inFile.getName()));
//			httppost.setEntity(entity);
//			// execute the request
//			HttpResponse response = httpclient.execute(httppost);
//			
//			int statusCode = response.getStatusLine().getStatusCode();
//			HttpEntity responseEntity = response.getEntity();
//			String responseString = EntityUtils.toString(responseEntity, "UTF-8");
//			
//			System.out.println("[" + statusCode + "] " + responseString);
//			
//		} catch (ClientProtocolException e) {
//			System.err.println("Unable to make connection");
//			e.printStackTrace();
//		} catch (IOException e) {
//			System.err.println("Unable to read file");
//			e.printStackTrace();
//		} finally {
//			try {
//				if (fis != null) fis.close();
//			} catch (IOException e) {}
//		}
//	}
//	}
}
