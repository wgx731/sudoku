package ver_2;

import java.io.File;

import javax.swing.filechooser.FileFilter;


public class sudokuFileFilter extends FileFilter {

	String[] extensions;
	  String description;

	  public sudokuFileFilter(String ext) {
	    this (new String[] {ext}, null);
	  }

	  public sudokuFileFilter(String[] exts, String descr) {
	    // Clone and lowercase the extensions
	    extensions = new String[exts.length];
	    for (int i = exts.length - 1; i >= 0; i--) {
	      extensions[i] = exts[i].toLowerCase();
	    }
	    // Make sure we have a valid (if simplistic) description
	    description = (descr == null ? exts[0] + " files" : descr);
	  }

	  public boolean accept(File f) {
	    if (f.isDirectory()) { return true; }
	    String path = f.getAbsolutePath().toLowerCase();
	    String name = f.getName().toLowerCase();
	    for (int i = extensions.length - 1; i >= 0; i--) {
	      if (name.endsWith(extensions[i]) && (path.charAt(path.length() - extensions[i].length() - 1) == '.')) {
	        return true;
	      }
	    }
	    return false;
	  }

	  public String getDescription() { return description; }
}