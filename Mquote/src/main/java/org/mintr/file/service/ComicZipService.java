package org.mintr.file.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.mintr.util.ZipUtil;

public class ComicZipService {
	public static int TOTAL_FOLDER_PER_ZIP = 5;

	public List<String> zipAllComics(File root) {
		return null;
	}

	public List<String> zipSubFolders(File root) {
		List<String> zipFilePaths = new ArrayList<String>();
		if (!root.isDirectory()) return zipFilePaths;
		File[] subFoldersToZip = new File[TOTAL_FOLDER_PER_ZIP];

		for (File subFolder : root.listFiles()) {
			if (subFolder.isDirectory()) subFoldersToZip[subFoldersToZip.length] = subFolder;

			if (subFoldersToZip.length == TOTAL_FOLDER_PER_ZIP) {
				try {
					String fileName = aggregateFileName(subFoldersToZip) + ".zip";
					zipFilePaths.add(fileName);
					ZipUtil.zip(subFoldersToZip, new File(fileName));
				} catch (IOException e) {
					//kljsdaglk43j59o3
				}
			}
		}

		return zipFilePaths;
	}

	public String aggregateFileName(File[] folders) {
		List<String> folderNames = new ArrayList<String>(folders.length);
		for (File f : folders) {
			folderNames.add(f.getName());
		}
		return aggregateFileName(folderNames);
	}

	public String aggregateFileName(List<String> folderNames) {
		if (folderNames.size() < 2) return folderNames.get(0);

		String commonChars = folderNames.get(0).replaceAll("[0-9/\\[/\\]]", "");
		List<String> chapters  = new ArrayList<String>();

		for (String name : folderNames) {
			commonChars = commonChars.replaceAll("[^"+Pattern.quote(name)+"]","");
			chapters.add(name.replaceAll("[^0-9]", ""));
		}
		return commonChars + chapters;
	}
}
