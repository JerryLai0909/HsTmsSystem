/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.bluetooth;

import android.content.Context;
import android.os.RemoteException;

import com.gprinter.aidl.GpService;
import com.infinity.jerry.yyd_tms.data.BillingDrawMain;

import java.io.UnsupportedEncodingException;

/**
 * Created by root on 4/8/17.
 */

public class ZPrintUtil {

    private GpService mGpService;

    public ZPrintUtil() {
    }

    public void printJb() {
        if (mGpService == null) {
        } else {
            try {
                int type = mGpService.getPrinterCommandType(1);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void printXyBill(final BillingDrawMain printEntity, final int count, final boolean isSign, final Context context) {
    }


    private int strToNumber(String sth) {
        if (sth == null || sth.equals("")) {
            return 0;
        }
        try {
            return Integer.valueOf(sth);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private int inteToNumber(Integer sth) {
        if (sth == null) {
            return 0;
        }
        return sth;
    }

//
//    public void printXyLoad(final LoadPrintAllEntity loadEntities) {
//
//        binder.writeDataByYouself(new UiExecute() {
//            @Override
//            public void onsucess() {
//                Log.e("TAG", "成功连接");
//            }
//
//            @Override
//            public void onfailed() {
//                Log.e("TAG", "连接失败");
//            }
//        }, new ProcessData() {
//            @Override
//            public List<byte[]> processDataBeforeSend() {
//                List<byte[]> list = new ArrayList<>();
//                CarDriverDetailEntity carEntity = loadEntities.getEntity();
//                List<PrintResponseEntity> billEntity = loadEntities.getPrintEntity();
//
//                list.add(DataForSendToPrinterPos80.initializePrinter());
//                list.add(DataForSendToPrinterPos80.selectOrCancelChineseCharDoubleWH(1));
//                list.add(DataForSendToPrinterPos80.selectAlignment(1));
//                list.add(strTobytes(AppUserToken.getInstance().getCompany_name() + "货运单\n"));
//                list.add(DataForSendToPrinterPos80.printAndFeedLine());
//                list.add(strTobytes(billEntity.get(0).getInitial_station() + " - " + billEntity.get(0).getTerminal_station() + "\n"));
//
//                list.add(DataForSendToPrinterPos80.printAndFeedLine());
//                list.add(DataForSendToPrinterPos80.printAndFeedLine());
//
//
//                EscCommand esc = new EscCommand();
//                esc.addInitializePrinter();
//                esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
//                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
//
//                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
//                esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
//                esc.addText("车牌号 " + carEntity.getPlate_numbers() + "  " + carEntity.getDriver_name() + " " + carEntity.getDriver_phone());
//                esc.addPrintAndLineFeed();
//                esc.addText("批次号 " + carEntity.getBatch_number());
//                esc.addPrintAndLineFeed();
//                esc.addText("------------------------------------------------");
//                //目的地
//                esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
////        esc.addText("目的地 : "+carEntity.getDestination1()+" -> "+carEntity.getDestination2() + " -> "+carEntity.getDestination3());
////        esc.addPrintAndLineFeed();
//                esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
//                esc.addText("货号");
//                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
////        esc.addSetAbsolutePrintPosition((short) 6);
//                esc.addSetAbsolutePrintPosition((short) 5);
//                esc.addText("包装");
//                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
////        esc.addSetAbsolutePrintPosition((short) 14);
//                esc.addSetAbsolutePrintPosition((short) 11);
//                esc.addText("到付");
//                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
////        esc.addSetAbsolutePrintPosition((short) 18);
//                esc.addSetAbsolutePrintPosition((short) 14);
//                esc.addText("现付");
//                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
////        esc.addSetAbsolutePrintPosition((short) 18);
//                esc.addSetAbsolutePrintPosition((short) 18);
//                esc.addText("签收人");
//
//                //TODO for 循环
//                esc.addPrintAndLineFeed();
//                esc.addText("------------------------------------------------");
//                esc.addPrintAndLineFeed();
//                int totalNumber = 0;//总件数
//                int totalcollo = 0;  //到付
//                int totalCash = 0;  //现付
//                for (PrintResponseEntity goodEntity : billEntity) {
//                    esc.addText(goodEntity.getArticle_number());
//                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
//                    esc.addSetAbsolutePrintPosition((short) 5);
//                    esc.addText(goodEntity.getPackaging());
//                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
//                    esc.addSetAbsolutePrintPosition((short) 11);
//
//                    String ariNumber = goodEntity.getArticle_number();
//                    if (ariNumber != null && !ariNumber.equals("")) {
//                        try {
//                            totalNumber += Integer.valueOf(ariNumber.split("-")[1]);
//                        } catch (Exception e) {
//                            continue;
//                        }
//                    }
//
//                    String collectPament = goodEntity.getCollect_payment();
//                    if (collectPament == null || collectPament.equals("")) {
//                        collectPament = "0";
//                    } else {
//                        try {
//                            totalcollo += Integer.valueOf(collectPament);
//                        } catch (Exception e) {
//                            continue;
//                        }
//                    }
//
//                    esc.addText(collectPament);//到付
//                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
//                    esc.addSetAbsolutePrintPosition((short) 14);
//
//                    String cashPayment = goodEntity.getCash_payment();
//                    if (cashPayment == null || cashPayment.equals("")) {
//                        cashPayment = "0";
//                    } else {
//                        try {
//                            totalCash += Integer.valueOf(cashPayment);
//
//                        } catch (Exception e) {
//                            continue;
//                        }
//                    }
//                    esc.addText(cashPayment);//现付
//                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
////        esc.addSetAbsolutePrintPosition((short) 18);
//                    esc.addSetAbsolutePrintPosition((short) 18);
//                    esc.addText(goodEntity.getConsignee());
//                    esc.addPrintAndLineFeed();
//                }
//
//                esc.addText("------------------------------------------------");
//                esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
//                esc.addText("总计:");
//                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
//                esc.addSetAbsolutePrintPosition((short) 5);
//                esc.addText(totalNumber + "件");
//                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
//                esc.addSetAbsolutePrintPosition((short) 11);
//                esc.addText(totalcollo + "");
//                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
//                esc.addSetAbsolutePrintPosition((short) 14);
//                esc.addText(totalCash + "");
//                esc.addPrintAndLineFeed();
//
//                //总计 票 件 运费
//                esc.addPrintAndLineFeed();
//                esc.addText("承运司机(签字): ");
//                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
//                esc.addSetAbsolutePrintPosition((short) 12);
//                esc.addText("交接人(签字): ");
//                esc.addPrintAndLineFeed();
//
//                esc.addText("发车网点: " + carEntity.getLoading_point_name());
//
//                esc.addPrintAndLineFeed();
//
//                esc.addText("制单: " + AppUserToken.getInstance().getUser_name());
//                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
//                esc.addSetAbsolutePrintPosition((short) 10);
//                esc.addText("打印时间 " + DateUtil.getPrintTime(System.currentTimeMillis()));
//                esc.addPrintAndLineFeed();
//                esc.addText("");
//                esc.addPrintAndLineFeed();
//                esc.addPrintAndFeedLines((byte) 3);
//                esc.addCutAndFeedPaper((byte) 3);
//
//                Vector<Byte> datas = esc.getCommand();//发送数据
//                Log.e("TAG", datas.toString());
//                byte[] bytes = GpUtils.ByteTo_byte(datas);
//                list.add(bytes);
//
//                return list;
//            }
//        });
//    }

    private Double getNotNullDouble(Double sth) {
        return sth != null ? sth : 0.0;
    }

    /**
     * 字符串转byte数组
     */
    public byte[] strTobytes(String str) {
        byte[] b = null, data = null;
        try {
            b = str.getBytes("utf-8");
            data = new String(b, "utf-8").getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return data;
    }


}
