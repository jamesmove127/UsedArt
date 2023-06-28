package com.seener.usedarts.notifications;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.seener.usedarts.R;

import java.util.Map;

public class FcmNotification extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // 处理接收到的推送通知消息
        if (remoteMessage.getNotification() != null) {
            // 获取通知的标题和内容
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

            // 自定义处理逻辑，例如显示通知、更新UI等
            showNotification(title, body);
        }

        // 处理接收到的数据消息
        if (remoteMessage.getData() != null) {
            // 获取数据消息的内容
            Map<String, String> data = remoteMessage.getData();

            // 自定义处理逻辑，例如解析数据、处理业务逻辑等
            processMessageData(data);
        }
    }

    private void showNotification(String title, String body) {
        // 创建通知渠道（仅适用于 Android 8.0 及以上版本）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "channel_id",
                    "Channel Name",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // 创建通知
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.icon)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // 显示通知
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, builder.build());
    }


    private void processMessageData(Map<String, String> data) {
        // 在这里实现处理数据消息的逻辑
        // 解析数据、处理业务逻辑等
    }

    @Override
    public void onNewToken(String token) {
        // 当新的 FCM token 生成时会调用此方法
        // 在这里处理您的逻辑，例如将新的 token 上传到服务器等
        Log.d("FCM", token);
    }


}

