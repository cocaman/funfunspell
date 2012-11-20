package org.mintr.file.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ComicZipService {
	public static int TOTAL_FOLDER_PER_ZIP = 5;

	public List<String> zipAllComics(File root) {
		
	}
	
	public List<String> zipSubFolders(File root) {
		List<String> zipFilePaths = new ArrayList<String>();		
		if (!root.isDirectory()) return zipFilePaths;
		File[] subFoldersToZip = new File[TOTAL_FOLDER_PER_ZIP];		
		
		for (File subFolder : root.listFiles()) {
			if (subFolder.isDirectory()) subFoldersToZip[subFoldersToZip.length] = subFolder;
			
			if (subFoldersToZip.length == TOTAL_FOLDER_PER_ZIP) {
				
			}
		}
		
		return zipFilePaths;	
	}
	
	public String aggregateFileName(File[] folders) {
		if (folders.length == 1) return folders[0].getName();
		
		for (File f : folders) {
			String folderName = f.getName();
			folderName.
		}
	}
}
