package me.kofesst.android.redminecomposeapp.feature.domain.repository

import me.kofesst.android.redminecomposeapp.feature.data.model.issue.Issue
import me.kofesst.android.redminecomposeapp.feature.data.model.issue.Tracker
import me.kofesst.android.redminecomposeapp.feature.data.model.project.Project
import me.kofesst.android.redminecomposeapp.feature.domain.model.CurrentUser

interface RedmineRepository {

    suspend fun getCurrentUser(host: String, apiKey: String): CurrentUser

    suspend fun getProjects(): List<Project>

    suspend fun getIssues(): List<Issue>

    suspend fun getIssueDetails(issueId: Int): Issue

    suspend fun getTrackers(): List<Tracker>
}