import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

import org.junit.Test;
import org.mintr.util.ZipUtil;
import org.springframework.util.FileSystemUtils;


public class TestFileObject {
	
	@Test public void testBoolean() {
		Boolean sphinxInFigure = Boolean.parseBoolean(null);
		Boolean isSuppressed = sphinxInFigure && Boolean.TRUE;
		System.out.println(isSuppressed);
	}

	@Test
	public void testZipFile() {		
		try {
			ZipUtil.zip(new File[]{new File("h:\\Sparta"), new File("h:\\mintr_data")}, new File("h:\\test.zip"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
