
var WshShell = WScript.CreateObject ("WScript.Shell");

var presenters = new Array();
var panels  = new Array();
var collections = new Array();
var theRest = new Array();


var fso
fso = new ActiveXObject("Scripting.FileSystemObject");
var folderspec = WshShell.CurrentDirectory; //Start and pass in current directory

  
  	
  var f = fso.GetFolder(folderspec);
  var fc = new Enumerator(f.files);
  for (; !fc.atEnd(); fc.moveNext()){
	  
	var fullPath = fc.item().name;
	var filename = fullPath.replace(/^.*[\\\/]/, '');
	  
	if(filename.indexOf("Presenter") != -1){
		
		presenters.push(filename);
	}
	else if(filename.indexOf("Panel") != -1){
		
		panels.push(filename);
	}
	else if(filename.indexOf("Collection")!= -1){
		
		collections.push(filename);
	}
	else{
		
		theRest.push(filename);
	}
  }
  presenters.sort();
  panels.sort();
  collections.sort();
  theRest.sort();

  // Give the generated files an extension that hides them from doxygen generation
  // so when you copy them in to the core.docs repository, you don't need to worry
  // about deleting them every time you generate docs
  var ext = ".js"
  // Give the filenames similar name they have in the core.docs repository
  // so when you copy them in to the core.docs repository, they'll sit next 
  // to their corresponding .cs file, so you can diff them and such.
  var prefix = "objectmodel_";
  writeDoxygenFileFromList(presenters  , prefix+"presenters" +ext);
  writeDoxygenFileFromList(panels        , prefix+"panels" + ext);
  writeDoxygenFileFromList(collections   , prefix+"collections" + ext);
  writeDoxygenFileFromList(theRest      , prefix+"therest" + ext);

function writeDoxygenFileFromList(theArray, theNameOfCSFile){
	
	var fs, otf, ForAppending;
	ForAppending = 8;
	fs = new ActiveXObject("Scripting.FileSystemObject");
	otf = fs.OpenTextFile(WshShell.CurrentDirectory+"\\"+ theNameOfCSFile, ForAppending, true);
	for(i=0;i<theArray.length;i++)
	{
		var text = constructDoxygenEntry(theArray[i]);
		otf.writeLine(text);
	}
	otf.Close(); //Close File for writing.
}


function constructDoxygenEntry(theClassName){
	
	var s="";
	s+="/*! @package com.github.a2g.core.objectmodel." + theClassName + "\r\n";
	s+="@brief \r\n";
	s+="@image */ \r\n";
	s+="\r\n";
	return s;
}
  
  