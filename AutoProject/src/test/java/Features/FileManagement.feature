Feature: File Management
	    Verify that user is able to create, delete and restore a file

@Login1
	Scenario: Login to https://mega.nz/login with valid username and password
		Given I open the URL: 'https://mega.nz/login'
		When I input email 'ron.kauri@gmail.com' and password 'Test123Auto!'
		And press 'Log in' button
		Then I see my cloud drive
		    
@Testcase1
	Scenario: Create a text file 
		Given I logged in MEGA page already
		When I click on folder 'Cloud Drive'
		And I right click on space and select option 'New text file'
		And I input file name 'a.txt' with content 'megatesting' and close file
		Then I see the file 'a.txt'

@Testcase2    
	Scenario: Delete a file
	    Given I logged in MEGA page already
		And I right click on file 'a.txt' and select option 'Remove'
		And I click on 'Yes' button on confirm message
		Then I cannot see the file 'a.txt'   
    
@Testcase3    
 	Scenario: Restore a file
		Given I logged in MEGA page already
		When I click on folder 'Rubbish Bin'
		And I click on view 'Thumbnail View'
		And I right click on file 'a.txt' and select option 'Restore'
		And I click on folder 'Cloud Drive'
		Then I see the file 'a.txt'

@CloseBrowser
	Scenario: Close the current browser 	 
		Then I close the current browser