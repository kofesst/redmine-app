package me.kofesst.android.redminecomposeapp.feature.data.repository

import com.google.gson.GsonBuilder
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import me.kofesst.android.redminecomposeapp.feature.data.model.issue.CreateIssueBody
import me.kofesst.android.redminecomposeapp.feature.data.model.issue.Issue
import me.kofesst.android.redminecomposeapp.feature.data.model.issue.IssuesResponse
import me.kofesst.android.redminecomposeapp.feature.data.model.issue.Tracker
import me.kofesst.android.redminecomposeapp.feature.data.model.membership.Membership
import me.kofesst.android.redminecomposeapp.feature.data.model.project.Project
import me.kofesst.android.redminecomposeapp.feature.data.model.status.Status
import me.kofesst.android.redminecomposeapp.feature.data.remote.RedmineApi
import me.kofesst.android.redminecomposeapp.feature.data.repository.util.DateDeserializer
import me.kofesst.android.redminecomposeapp.feature.data.repository.util.DateTime
import me.kofesst.android.redminecomposeapp.feature.data.repository.util.DateTimeDeserializer
import me.kofesst.android.redminecomposeapp.feature.domain.model.CurrentUser
import me.kofesst.android.redminecomposeapp.feature.domain.repository.RedmineRepository
import me.kofesst.android.redminecomposeapp.feature.domain.util.UserHolder
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.UnknownHostException
import java.util.*

class RedmineRepositoryImpl(
    private val userHolder: UserHolder,
) : RedmineRepository {

    @Throws(Exception::class)
    override suspend fun getCurrentUser(host: String, apiKey: String): CurrentUser {
        return handleResponse(host) { api ->
            api.getCurrentUser(apiKey)
        }.user.toCurrentUser(host, apiKey)
    }

    @Throws(Exception::class)
    override suspend fun getProjects(): List<Project> {
        return handleResponse(userHolder.host) { api ->
            api.getProjects(userHolder.apiKey)
        }.projects
    }

    @Throws(Exception::class)
    override suspend fun getProjectIssues(projectId: Int, offset: Int): IssuesResponse {
        return handleResponse(userHolder.host) { api ->
            api.getProjectIssues(
                apiKey = userHolder.apiKey,
                projectId = projectId,
                offset = offset
            )
        }
    }

    @Throws(Exception::class)
    override suspend fun getOwnedIssues(offset: Int): IssuesResponse {
        return handleResponse(userHolder.host) { api ->
            api.getOwnedIssues(
                apiKey = userHolder.apiKey,
                offset = offset
            )
        }
    }

    @Throws(Exception::class)
    override suspend fun getAssignedIssues(offset: Int): IssuesResponse {
        return handleResponse(userHolder.host) { api ->
            api.getAssignedIssues(
                apiKey = userHolder.apiKey,
                offset = offset
            )
        }
    }

    @Throws(Exception::class)
    override suspend fun getIssueDetails(issueId: Int): Issue {
        return handleResponse(userHolder.host) { api ->
            api.getIssueDetails(userHolder.apiKey, issueId)
        }.issue
    }

    @Throws(Exception::class)
    override suspend fun createIssue(issue: CreateIssueBody) {
        handleRequest(userHolder.host) { api ->
            api.createIssue(userHolder.apiKey, issue)
        }
    }

    @Throws(Exception::class)
    override suspend fun updateIssue(
        issueId: Int,
        issue: CreateIssueBody,
    ) {
        handleRequest(
            host = userHolder.host,
            useXml = true
        ) { api ->
            api.updateIssue(userHolder.apiKey, issueId, issue)
        }
    }

    @Throws(Exception::class)
    override suspend fun getTrackers(): List<Tracker> {
        return handleResponse(userHolder.host) { api ->
            api.getTrackers(userHolder.apiKey)
        }.trackers
    }

    @Throws(Exception::class)
    override suspend fun getStatuses(): List<Status> {
        return handleResponse(userHolder.host) { api ->
            api.getStatuses(userHolder.apiKey)
        }.issue_statuses
    }

    @Throws(Exception::class)
    override suspend fun getMembers(projectId: Int): List<Membership> {
        return handleResponse(userHolder.host) { api ->
            api.getMembers(userHolder.apiKey, projectId)
        }.memberships
    }

    @Throws(Exception::class)
    private suspend fun handleRequest(
        host: String,
        useXml: Boolean = false,
        request: suspend (RedmineApi) -> Unit,
    ) {
        try {
            request(
                buildApi(
                    host = host,
                    useXml = useXml
                )
            )
        } catch (hostException: UnknownHostException) {
            throw Exception("Хост не найден")
        } catch (nullException: NullPointerException) {
            throw Exception("Тело ответа пустое")
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

    @Throws(Exception::class)
    private suspend fun <T> handleResponse(
        host: String,
        request: suspend (RedmineApi) -> Response<T>,
    ): T {
        try {
            val response = request(buildApi(host))

            if (response.code() == 401) {
                throw Exception("Неверный API-ключ")
            }

            if (!response.isSuccessful) {
                throw Exception("Код ошибки: ${response.code()}")
            }

            return response.body() ?: throw NullPointerException("")
        } catch (hostException: UnknownHostException) {
            throw Exception("Хост не найден")
        } catch (nullException: NullPointerException) {
            throw Exception("Тело ответа пустое")
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    private fun buildApi(
        host: String,
        useXml: Boolean = false,
    ): RedmineApi {
        val converterFactory: Converter.Factory = if (useXml) {
            TikXmlConverterFactory.create(
                TikXml.Builder()
                    .build()
            )
        } else {
            GsonConverterFactory.create(
                GsonBuilder()
                    .registerTypeAdapter(DateTime::class.java, DateTimeDeserializer())
                    .registerTypeAdapter(Date::class.java, DateDeserializer())
                    .create()
            )
        }

        val retrofit = Retrofit.Builder()
            .addConverterFactory(converterFactory)
            .baseUrl("https://$host")
            .build()

        return retrofit.create(RedmineApi::class.java)
    }
}