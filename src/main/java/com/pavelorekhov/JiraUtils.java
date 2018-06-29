package com.pavelorekhov;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.*;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class JiraUtils {


    public static final List<String> OPTIONS_LIST = Collections.unmodifiableList(Arrays.asList(
            "description",
            "comments",
            "assignee",
            "reporter",
            "priority",
            "status",
            "summary"
    ));
    public static final String issueInfoRegex = "\\w+-\\d+\\s+\\w+";
    private static String username;
    private static String password;

    private JiraUtils() {

    }

    public static void setPassword(String password) {
        JiraUtils.password = password;
    }

    public static void setUsername(String username) {
        JiraUtils.username = username;
    }

    private static Issue getIssue(String issueKey) throws Exception {
        final URI jiraServerUri = new URI("https://jira.leroymerlin.ru");
        final JiraRestClient restClient = new AsynchronousJiraRestClientFactory().createWithBasicHttpAuthentication(jiraServerUri, username, password);
        Promise issuePromise = restClient.getIssueClient().getIssue(issueKey);
        return Optional.ofNullable((Issue) issuePromise.claim()).orElseThrow(() -> new Exception("No such issue"));
    }

    public static String getIssueInfo(String taskId, String what) {
        Issue issue = null;
        try {
            issue = getIssue(taskId.toLowerCase());
        } catch (Exception e) {
            return "No such issue";
        }
        switch (what.toLowerCase()) {
            case "description":
                return issue.getDescription();
            case "comments":
                Iterable<Comment> comments = issue.getComments();
                return "Too many comments";
            case "assignee":
                User assignee = issue.getAssignee();
                if (assignee != null) {
                    return assignee.getDisplayName();
                }
                return "Unassigned";
            case "reporter":
                User reporter = issue.getReporter();
                if (reporter != null) {
                    return reporter.getDisplayName();
                }
                return "No reporter";
            case "priority":
                BasicPriority priority = issue.getPriority();
                if (priority != null) {
                    return priority.getName();
                }
                return "No priority set";
            case "status":
                Status status = issue.getStatus();
                if (status != null) {
                    return status.getName();
                }
                return "No status";
            case "summary":
                return issue.getSummary();
            default:
                return "Illegal option, please provide an option from this list: " + OPTIONS_LIST;
        }
    }
}
