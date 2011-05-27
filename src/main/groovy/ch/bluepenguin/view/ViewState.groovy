package ch.bluepenguin.view

class ViewState {
	
	def components
	def model
	def toDisplayNode() {
		components.nodeDetails.visible=true
	}
	def toLogin() {
		components.browser.readOnly=false
		components.messageLabel.caption="Welcome ${model.userID}, You are logged in to Workspace ${model.workspace}"
		components.login.enabled = false
		components.logout.enabled = true
	}

	def toLogout() {
		components.browser.readOnly=true
		components.messageLabel.caption="Please log in"
		components.login.enabled = true
		components.logout.enabled = false
	}
	/**	
	root {
		loggedOut { 
			loggedIn {
				loggedOut
				browser
			} 
		
		
	}
	State root
	
	
**/
}

