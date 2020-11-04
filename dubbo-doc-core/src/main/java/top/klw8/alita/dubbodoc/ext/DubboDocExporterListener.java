package top.klw8.alita.dubbodoc.ext;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Exporter;
import org.apache.dubbo.rpc.ExporterListener;
import org.apache.dubbo.rpc.RpcException;

/**
 * .
 *
 * @author klw(213539 @ qq.com)
 * @date 2020/10/29 10:50
 */
@Activate
public class DubboDocExporterListener implements ExporterListener {
    @Override
    public void exported(Exporter<?> exporter) throws RpcException {
        System.out.println("=============exported=============");
    }

    @Override
    public void unexported(Exporter<?> exporter) {
        System.out.println("=============unexported=============");
    }
}
