package com.akinci.projectfinder.data.projects

import com.akinci.projectfinder.core.network.toResponse
import com.akinci.projectfinder.data.projects.remote.ProjectResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject

class ProjectRepository @Inject constructor(
    private val httpClient: HttpClient,
) {

    suspend fun getProjectRepositories(repositoryOwnerName: String) =
        runCatching {
            httpClient
                .get("/users/$repositoryOwnerName/repos")
                .toResponse<List<ProjectResponse>>()
                .map { repositories ->
                    // we need to map network data to domain model
                    //  checking local stored favorite information
                    repositories.map {
                        it.toDomain().copy(
                            // TODO fix here.
                            isFavorite = false
                        )
                    }
                }.getOrThrow()
        }

}
