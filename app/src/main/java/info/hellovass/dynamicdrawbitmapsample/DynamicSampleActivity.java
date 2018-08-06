package info.hellovass.dynamicdrawbitmapsample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import info.hellovass.dynamicdrawbitmap.library.core.DynamicShareView;
import info.hellovass.dynamicdrawbitmap.library.entity.Bottom;
import info.hellovass.dynamicdrawbitmap.library.utils.MyAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hello on 2017/12/6.
 */

public class DynamicSampleActivity extends AppCompatActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_dynamic_sample);

    initWidgets();
  }

  private void initWidgets() {

    findViewById(R.id.btn_share).setOnClickListener(new View.OnClickListener() {

      @Override public void onClick(View v) {

        shareImage();
      }
    });
  }

  private void shareImage() {

    final DynamicShareView dynamicShareView = new DynamicShareView(this);
    dynamicShareView.setAdapter(
        new MyAdapter(generateImageUrls(), generateBottom(), new MyAdapter.OnImageLoadedListener() {

          @Override public void onImageLoaded() {

            DynamicShareViewTool.create(DynamicSampleActivity.this).share(dynamicShareView);
          }
        }));
  }

  private List<String> generateImageUrls() {
    List<String> results = new ArrayList<>();
    results.add("http://7xsq1h.com1.z0.glb.clouddn.com/Android%20Studio%20Proxy");
    results.add("http://7xsq1h.com1.z0.glb.clouddn.com/Android%20Studio%20Proxy");
    results.add("http://7xsq1h.com1.z0.glb.clouddn.com/Android%20Studio%20Proxy");
    results.add("http://7xsq1h.com1.z0.glb.clouddn.com/Android%20Studio%20Proxy");
    return results;
  }

  private Bottom generateBottom() {

    return new Bottom() {

      @Override public String getTitle() {

        return "4 万粉福利第一弹！！";
      }

      @Override public String getNickname() {

        return "Yuki";
      }

      @Override public String getAvatarUrl() {

        return "http://7xsq1h.com1.z0.glb.clouddn.com/Android%20Studio%20Proxy";
      }

      @Override public String getQRCodeUrl() {

        return "https://www.baidu.com/";
      }
    };
  }
}
