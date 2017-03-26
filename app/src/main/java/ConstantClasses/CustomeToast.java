package ConstantClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.swifttransport.R;

/**
 * Created by Sunil on 2/27/2017.
 */

//custome toast class
public class CustomeToast {

    public void CustomeToastSetting(Context context,String text){
        Toast toast=new Toast(context);
        //setting lenght long
        toast.setDuration(Toast.LENGTH_LONG);
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //setting custome layout
        View view=inflater.inflate(R.layout.toastlayout,null);
        TextView textView=(TextView)view.findViewById(R.id.toastTextView);
        textView.setText(text);
        toast.setView(view);
        toast.show();
    }
}
