package com.example.egarden;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;


import java.io.File;
import java.util.Calendar;
import java.util.Map;


public class Tools {

    private static final int KEEP_ME_ALIVE_FLAG = 1112;

    public static void hideKeyboard(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    public static void getTime(Activity activity, TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minutes) {
                onTimeSetListener.onTimeSet(timePicker, hour, minutes);
            }
        }, hour, minute,
                DateFormat.is24HourFormat(activity)).show();
    }

    public static void getDate(Activity activity, DatePickerDialog.OnDateSetListener onDateSetListener) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                onDateSetListener.onDateSet(datePicker, i, i1 + 1, i2);
            }
        }, year, month, day).show();
    }

   /* public static void toast(String msg) {
        Toast.makeText(Jute.getContext(), msg, Toast.LENGTH_LONG).show();
    }*/

    public static void print(String str) {
        Log.e(">>>>> ", str);
    }

    public static void print(int str) {
        Log.e(">>>>> ", String.valueOf(str));
    }






    public static void showSnackbar(Activity activity,
                                    final String mainTextStringId, final String actionStringId, int LENGTH,
                                    View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(
                activity.findViewById(android.R.id.content),
                mainTextStringId,
                LENGTH)
                .setActionTextColor(Color.WHITE)
                .setAction(actionStringId, listener);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        snackbar.show();
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static void showSnackbarWithColor(
            Activity activity,
            final String mainTextString,
            final String actionString,
            int LENGTH,
            View.OnClickListener listener, int color) {
        Snackbar snackbar = Snackbar.make(
                activity.findViewById(android.R.id.content),
                mainTextString,
                LENGTH)
                .setActionTextColor(Color.WHITE)
                .setAction(actionString, listener);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(activity, color));
        snackbar.show();
    }

    public static void snackErr(Activity activity,
                                final String mainTextString,
                                View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(
                activity.findViewById(android.R.id.content),
                mainTextString,
                Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(Color.WHITE)
                .setAction("OK", listener);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.red));
        snackbar.show();
    }

    public static void snackOK(Activity activity,
                               final String mainTextStringId,
                               View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(
                activity.findViewById(android.R.id.content),
                mainTextStringId,
                Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(Color.WHITE)
                .setAction("OK", listener);

        snackbar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        snackbar.show();
    }

    public static void snackInfo(Activity activity,
                                 final String mainTextStringId,
                                 View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(
                activity.findViewById(android.R.id.content),
                mainTextStringId,
                Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.WHITE)
                .setAction("OK", listener);

        snackbar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        snackbar.show();
    }

    public static void snackInfo(Activity activity, final String body) {
        Snackbar snackbar = Snackbar.make(
                activity.findViewById(android.R.id.content),
                body,
                Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.WHITE)
                .setAction("OK", null);

        snackbar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.teal_700));
        snackbar.show();
    }

    public static void snackErrInfo(Activity activity,
                                    final String mainTextStringId,
                                    View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(
                activity.findViewById(android.R.id.content),
                mainTextStringId,
                Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.WHITE)
                .setAction("OK", listener);

        snackbar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.red));
        snackbar.show();
    }

/*    public static void showError(final Activity context, String errString) {
        final Dialog dialog = new Dialog(context, R.style.Theme_AppCompat);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialogue_error);
        //dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final TextView error = dialog.findViewById(R.id.error);
        final AppCompatImageView close = dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        error.setText(errString);
        dialog.show();
    }*/

    public static void showNotification(Context context, String title, String msg, Integer id, Map<String, String> data) {

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "GariSeba");

 /*       Intent orders = new Intent(context, OrderDetail.class)
                .putExtra(JsonParser.Action.action, data == null ? "none" : data.get("action"))
                .putExtra(JsonParser.Status.status, "none")
                .putExtra(drug_store_id, data == null ? "1" : data.get("id"));


        Intent announcements = new Intent(
                context, Announcement.class)
                .putExtra(JsonParser.Action.action, JsonParser.Action.announcements);

        Intent notifications = new Intent(
                context, Announcement.class)
                .putExtra(JsonParser.Action.action, JsonParser.Action.notifications);


        Intent resultIntent = new Intent(context, Home.class);

        if (data != null && data.get("action") != null) {
            String action = data.get("action");
            Log.e("Notification", action);

            if (action.equals(JsonParser.Action.announcements)) {
                resultIntent = announcements;
            }
            if (action.equals(JsonParser.Action.notifications)) {
                resultIntent = notifications;
            }

            if (action.equals(JsonParser.Action.deliveries)
                    || action.equals(JsonParser.Action.invoices)
                    || action.equals(JsonParser.Action.orders)) {
                resultIntent = orders;
            }

        }
*/

        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
     /*   stackBuilder.addNextIntentWithParentStack(new Intent(context, DashBoard.class));*/
        // Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);

      /*  mBuilder.setSmallIcon(R.drawable.ic_notifications);*/
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(msg);
        mBuilder.setSound(defaultSoundUri);
        mBuilder.setVibrate(new long[]{1000, 0, 1000, 0});
        mBuilder.setContentIntent(resultPendingIntent);
        // mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("1000", "abc", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            //notificationChannel.setVibrationPattern(new long[]{1000,0, 1000,0});
            assert mNotificationManager != null;
            mBuilder.setChannelId("1000");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }

        mNotificationManager.notify(id, mBuilder.build());

    }


/*    public static void setKeepMeAlive(Context context, int mills) {
        Log.e("setKeepMeAliveIntent", "set");
        // setKeepMeAliveStatus(true);
        Intent alarmIntent = new Intent(context, KeepMeAliveReceiver.class);
        Akij.setKeepMeAliveIntent(alarmIntent);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, KEEP_ME_ALIVE_FLAG, Akij.getKeepMeAliveIntent(), PendingIntent.FLAG_UPDATE_CURRENT);
        Akij.setKeepMeAlivePendingIntent(pendingIntent);
        AlarmManager manager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), mills, pendingIntent);
    }

    public static void cancelKeepMeAlive(Context context) {
        //setKeepMeAliveStatus(false);
        Log.e("cancelcancelKeepMeAlive", "cancelled");
        // Intent alarmIntent = new Intent(context, KeepMeAliveReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, KEEP_ME_ALIVE_FLAG, Akij.getKeepMeAliveIntent(), PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.cancel(Akij.getKeepMeAlivePendingIntent());
        Akij.getKeepMeAlivePendingIntent().cancel();
    }*/

   /* @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createPDF() {
        final File file = new File(uploadFolder, "AnswerSheet_" + queId + ".pdf");

        final ProgressDialog dialog = ProgressDialog.show(this, "", "Generating PDF...");
        dialog.show();
        new Thread(() -> {
            Bitmap bitmap;
            PdfDocument document = new PdfDocument();
            //  int height = 842;
            //int width = 595;
            int height = 1010;
            int width = 714;
            int reqH, reqW;
            reqW = width;

            for (int i = 0; i < array.size(); i++) {
                //  bitmap = BitmapFactory.decodeFile(array.get(i));
                bitmap = Utility.getCompressedBitmap(array.get(i), height, width);


                reqH = width * bitmap.getHeight() / bitmap.getWidth();
                Log.e("reqH", "=" + reqH);
                if (reqH < height) {
                    //  bitmap = Bitmap.createScaledBitmap(bitmap, reqW, reqH, true);
                } else {
                    reqH = height;
                    reqW = height * bitmap.getWidth() / bitmap.getHeight();
                    Log.e("reqW", "=" + reqW);
                    //   bitmap = Bitmap.createScaledBitmap(bitmap, reqW, reqH, true);
                }
                // Compress image by decreasing quality
                // ByteArrayOutputStream out = new ByteArrayOutputStream();
                //  bitmap.compress(Bitmap.CompressFormat.WEBP, 50, out);
                //    bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
                //bitmap = bitmap.copy(Bitmap.Config.RGB_565, false);
                //Create an A4 sized page 595 x 842 in Postscript points.
                //PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(reqW, reqH, 1).create();
                PdfDocument.Page page = document.startPage(pageInfo);
                Canvas canvas = page.getCanvas();

                Log.e("PDF", "pdf = " + bitmap.getWidth() + "x" + bitmap.getHeight());
                canvas.drawBitmap(bitmap, 0, 0, null);

                document.finishPage(page);
            }

            FileOutputStream fos;
            try {
                fos = new FileOutputStream(file);
                document.writeTo(fos);
                document.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            runOnUiThread(() -> {
                dismissDialog(dialog);

            });
        }).start();
    }*/

    public static void openPDF(final Activity context, String path) {


        Intent intent = new Intent(Intent.ACTION_VIEW);
        File file = new File(path, "demo.pdf");
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        context.startActivity(intent);
    }


}
