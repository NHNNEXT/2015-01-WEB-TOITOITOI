package cafein.file;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cafein.util.ImageFileUtils;
import cafein.util.Result;

@RestController
public class APIFileController {
	private static final Logger logger = LoggerFactory.getLogger(APIFileController.class);
	private static final String filePath = "/root/images";

	@Autowired
	private FileDAO filedao;

	@RequestMapping(value = "/api/post/file", method = RequestMethod.POST)
	public Result insertFile(MultipartHttpServletRequest request) {
		Iterator<String> iterator = request.getFileNames();
		MultipartFile multipartFile = null;
		while (iterator.hasNext()) {
			multipartFile = request.getFile(iterator.next());
			if (multipartFile.isEmpty() == false) {
				logger.debug("filename : " + multipartFile.getOriginalFilename());

				File file = new File(filePath);
				if (file.exists() == false) {
					file.mkdirs();
				}

				String originalFileName = multipartFile.getOriginalFilename();
				String originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
				String storedFileName = ImageFileUtils.getRandomString() + originalFileExtension;

				file = new File(filePath + storedFileName);

				try {
					multipartFile.transferTo(file);
				} catch (IOException e) {
					return Result.failed(e.toString());
				}

				ImageFile imageFile = new ImageFile(originalFileName, storedFileName);
				filedao.addFileInfo(imageFile);
				return Result.success(storedFileName);
			}
		}
		return Result.failed("multipartFile is null");
	}

	@RequestMapping(value = "/api/post/{postid}/file", method = RequestMethod.GET)
	public Result sendFile(@PathVariable(value = "postid") Integer postid, HttpServletResponse response)
			throws UnsupportedEncodingException {
		String storedFileName;
		String originalFileName;
		String originalFileExtension;
		
		try {
			storedFileName = filedao.getStroedFileNameByPostId(postid);
			originalFileName = filedao.getOriginalFileNameByPostId(postid);
			originalFileExtension = storedFileName.substring(storedFileName.lastIndexOf("."));
		} catch (EmptyResultDataAccessException e) {
			return Result.failed("No file : "+e.toString());
		}

		byte fileByte[];
		try {
			fileByte = FileUtils.readFileToByteArray(new File(filePath + storedFileName));
			response.setContentType("image/" + originalFileExtension);
			response.setContentLength(fileByte.length);
			response.setHeader("Content-Disposition",
					"attachment; fileName=" + URLEncoder.encode(originalFileName, "UTF-8") + ";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.getOutputStream().write(fileByte);

			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			return Result.failed(e.toString());
		}
		return Result.success();
	}

}
