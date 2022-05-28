package me.kofesst.android.redminecomposeapp.domain.repository

import me.kofesst.android.redminecomposeapp.domain.model.*
import java.io.File

interface RedmineRepository {

    suspend fun getCurrentUser(host: String, apiKey: String): CurrentUser

    suspend fun getProjects(): List<Project>

    suspend fun getProjectIssues(projectId: Int, offset: Int): ItemsPage<Issue>

    suspend fun getOwnedIssues(offset: Int): ItemsPage<Issue>

    suspend fun getAssignedIssues(offset: Int): ItemsPage<Issue>

    suspend fun getIssueDetails(issueId: Int): Issue

    suspend fun createIssue(issue: Issue, attachments: List<UploadData>)

    suspend fun updateIssue(
        issueId: Int, issue: Issue,
        attachments: List<UploadData>, notes: String?,
    )

    suspend fun getTrackers(): List<IdName>

    suspend fun getStatuses(): List<IdName>

    suspend fun getMembers(projectId: Int): List<ProjectMember>

    suspend fun uploadFile(file: File, type: String): String

    suspend fun addAccount(account: Account)

    suspend fun getAccount(id: Int): Account?

    suspend fun getAccounts(): List<Account>

    suspend fun updateAccount(account: Account)

    suspend fun deleteAccount(account: Account)

    suspend fun saveSession(host: String, apiKey: String)

    suspend fun restoreSession(): Pair<String, String>?

    suspend fun clearSession()
}