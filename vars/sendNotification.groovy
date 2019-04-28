#!/usr/bin/env groovy

/**
 * Send notifications based on build status string
 */
import hudson.model.*

def call(String buildStatus = 'STARTED') {
	// build status of null means successful
	buildStatus =  buildStatus ?: 'SUCCESSFUL'

	// Default values
  def duration = currentBuild.durationString.replace(" and counting", "")
	def color = 'warning'
  def summary = "${env.JOB_NAME} - #${env.BUILD_NUMBER} "
  	
  // Override default values based on build status
  if (buildStatus == 'STARTED') {
    summary += "Starting build "
  } else if (buildStatus == 'SUCCESS') {
    color = 'good'
    colorCode = '#00FF00'
    summary += "Success after ${duration} "
  } else if (buildStatus) {
    color = 'danger'
    summary += "Failed after ${duration} "
  }

  summary += "(<${env.BUILD_URL}/display/redirect|Open>)"

  // Send notifications
  slackSend (color: color, message: summary)
}
