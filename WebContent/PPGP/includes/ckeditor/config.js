/**
 * @license Copyright (c) 2003-2018, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here.
	// For complete reference see:
	// https://ckeditor.com/docs/ckeditor4/latest/api/CKEDITOR_config.html
	config.extraPlugins = 'wordcount,notification'; 	
	config.wordcount = {
		    // Whether or not you want to show the Paragraphs Count
		    showParagraphs: false,
		    // Whether or not you want to show the Word Count
		    showWordCount: false,
		    // Whether or not you want to show the Char Count
		    showCharCount: true,
		    // Whether or not you want to count Spaces as Chars
		    countSpacesAsChars: true,
		    // Whether or not to include Html chars in the Char Count
		    countHTML: true,		    
		    // Maximum allowed Word Count, -1 is default for unlimited
		    maxWordCount: -1,
		    // Maximum allowed Char Count, -1 is default for unlimited
		    maxCharCount: 2450,
	};
	
	// The toolbar groups arrangement, optimized for a single toolbar row.
	config.toolbarGroups = [
		{ name: 'document',	   groups: [ 'mode', 'document', 'doctools' ] },
		{ name: 'clipboard',   groups: [ 'clipboard', 'undo' ] },
		{ name: 'editing',     groups: [ 'find', 'selection', 'spellchecker' ] },
		{ name: 'forms' },
		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
		{ name: 'paragraph',   groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ] },
		{ name: 'links' },
		{ name: 'insert' },
		{ name: 'styles' },
		{ name: 'colors' },
		{ name: 'tools' },
		{ name: 'others' },
		{ name: 'about' }
	];

	// The default plugins included in the basic setup define some buttons that
	// are not needed in a basic editor. They are removed here.
	//config.removeButtons = 'Cut,Copy,Paste,Undo,Redo,Anchor,Underline,Strike,Subscript,Superscript';
	config.removeButtons = 'Anchor,Underline,Strike,Subscript,Superscript,Indent,Outdent,About';
	// Dialog windows are also simplified.
	config.removeDialogTabs = 'link:advanced';
};
