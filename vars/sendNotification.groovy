#!/usr/bin/env groovy

/**
 * Send notifications based on build status string
 */
import hudson.model.*

def call(String buildStatus = 'STARTED') {
	// build status of null means successful
	buildStatus =  buildStatus ?: 'SUCCESSFUL'

	// Default values
  def duration = currentBuild.durationString
	def colorCode = '#FF0000'
  def summary = "${env.JOB_NAME} - #${env.BUILD_NUMBER} "
  	
  // Override default values based on build status
  if (buildStatus == 'STARTED') {
    color = 'YELLOW'
    colorCode = '#FFFF00'
    summary += "Starting build "
  } else if (buildStatus == 'SUCCESSFUL') {
    color = 'GREEN'
    colorCode = '#00FF00'
    summary += "Success after ${duration} "
  } else if (buildStatus) {
    color = 'RED'
    colorCode = '#FF0000'
    summary += "Failed after x "
  }

  summary += "(<${env.BUILD_URL}|Open>)"

  // Send notifications
  slackSend (color: colorCode, message: summary)
}