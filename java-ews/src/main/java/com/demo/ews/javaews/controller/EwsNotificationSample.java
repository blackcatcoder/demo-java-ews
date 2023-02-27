package com.demo.ews.javaews.controller;


import java.net.URI;
import java.util.UUID;

import com.microsoft.graph.authentication.*;
import com.microsoft.graph.models.extensions.*;
import com.microsoft.graph.requests.extensions.*;

public class GraphNotificationSample {

    public static void main(String[] args) throws Exception {

        String clientId = "your-client-id";
        String clientSecret = "your-client-secret";
        String tenantId = "your-tenant-id";
        String username = "your-username";
        String password = "your-password";

        IAuthenticationProvider authProvider = new UsernamePasswordProvider(clientId, clientSecret, username, password, tenantId);

        GraphServiceClient graphClient = GraphServiceClient.builder()
                .authenticationProvider(authProvider)
                .buildClient();

        UUID subscriptionId = UUID.randomUUID();

        Calendar calendar = graphClient.me().calendar().buildRequest().get();

        Subscription subscription = new Subscription();
        subscription.id = subscriptionId.toString();
        subscription.resource = "/users/" + calendar.owner.id + "/events";
        subscription.changeType = "created";
        subscription.notificationUrl = "https://your-notification-url";
        subscription.expirationDateTime = DateTime.parseRfc3339("2023-01-01T00:00:00.000Z");
        subscription.clientState = "your-client-state";

        Subscription newSubscription = graphClient.subscriptions().buildRequest().post(subscription);

        System.out.println("New subscription created: " + newSubscription.id);
    }
}
