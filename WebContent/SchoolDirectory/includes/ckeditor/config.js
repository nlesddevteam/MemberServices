/**
 * @license Copyright (c) 2003-2020, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here.
	// For complete reference see:
	// https://ckeditor.com/docs/ckeditor4/latest/api/CKEDITOR_config.html
	
config.toolbarGroups = [
		{ name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
		{ name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
		{ name: 'editing', groups: [ 'find', 'selection', 'spellchecker', 'editing' ] },
		{ name: 'forms', groups: [ 'forms' ] },
		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
		{ name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi', 'paragraph' ] },
		{ name: 'links', groups: [ 'links' ] },
		{ name: 'insert', groups: [ 'insert' ] },
		{ name: 'styles', groups: [ 'styles' ] },
		{ name: 'colors', groups: [ 'colors' ] },
		{ name: 'tools', groups: [ 'tools' ] },
		{ name: 'others', groups: [ 'others' ] },
		{ name: 'about', groups: [ 'about' ] }
	];

	config.removeButtons = 'Subscript,Superscript,Image,FontAwesome,Flash,Iframe,Club,Diam,Heart,Spade,Bwimage,Bwlink,CopyFormatting,RemoveFormat,Form,Checkbox,Radio,TextField,Textarea,Select,Button,ImageButton,HiddenField,Cut,Copy,Paste,PasteText';
	
	
	
config.extraPlugins = 'wordcount,notification'; 	
	config.wordcount = {
		    // Whether or not you want to show the Paragraphs Count
		    showParagraphs:true,
		    // Whether or not you want to show the Word Count
		    showWordCount: true,
		    // Whether or not you want to show the Char Count
		    showCharCount: true,
		    // Whether or not you want to count Spaces as Chars
		    countSpacesAsChars: true,
		    // Whether or not to include Html chars in the Char Count
		    countHTML: true,		    
		    // Maximum allowed Word Count, -1 is default for unlimited
		    maxWordCount: -1,
		    // Maximum allowed Char Count, -1 is default for unlimited
		    maxCharCount: 2250,
	};
	// The toolbar groups arrangement, optimized for a single toolbar row.

	// Dialog windows are also simplified.
	config.removeDialogTabs = 'link:advanced';
};
