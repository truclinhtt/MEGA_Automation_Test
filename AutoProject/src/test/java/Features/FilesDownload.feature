Feature: File Download
	    Verify that user is able to download files from MEGA server

@Download_Linux_Destros
	Scenario: Validate all the Linux Distros are downloadable
		Given I open the URL: 'https://mega.nz/sync' to download
		When I select platform 'Linux'
		And I click on All Downloads link
		And I download all Linux Distros in all folders
		Then all files are downloaded
		
@CloseBrowser
	Scenario: Close all the browsers 	 
		Then I close all the download browsers	 