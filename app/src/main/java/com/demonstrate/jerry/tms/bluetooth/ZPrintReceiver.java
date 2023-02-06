/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.bluetooth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.gprinter.command.EscCommand;
import com.gprinter.command.GpUtils;
import com.infinity.jerry.yyd_tms.data.AppUserToken;
import com.infinity.jerry.yyd_tms.data.BillingDrawMain;
import com.infinity.jerry.yyd_tms.ui.activity.MainActivity;
import com.infinity.jerry.yyd_tms.utils.DateUtil;
import com.infinity.jerry.yyd_tms.utils.StringUtils;
import com.infinity.jerry.yyd_tms.utils.ZDoubleFormat;
import com.infinity.jerry.yyd_tms.utils.ZShrPrefencs;

import net.posprinter.posprinterface.ProcessData;
import net.posprinter.posprinterface.UiExecute;
import net.posprinter.utils.DataForSendToPrinterPos80;
import net.posprinter.utils.DataForSendToPrinterTSC;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;


/**
 * Created by root on 2/16/17.
 */

public class ZPrintReceiver extends BroadcastReceiver {

    private Context context;
    private int requestState = 0;
    public static final int REQUEST_BILL = 64;
    public static final int REQUEST_LOAD = 128;

    public int copyNumber = 1;
    private boolean isSign = false;
    private BillingDrawMain printEntity;
    private List<BillingDrawMain> billEntity;

    private int buda = 0;

    public void setBuda(int buda) {
        this.buda = buda;
    }

    public ZPrintReceiver(Context context) {
        this.context = context;
    }

    public void xsetBillPrintEntity(BillingDrawMain entity, int requestState, int number, boolean isSign) {
        this.printEntity = entity;
        this.requestState = requestState;
        this.copyNumber = number;
        this.isSign = isSign;
    }

    public void setLoadPrintEntity(List<BillingDrawMain> billEntity, int requestState) {
        this.billEntity = billEntity;
        this.requestState = requestState;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        // GpCom.ACTION_DEVICE_REAL_STATUS 为广播的IntentFilter
        if (action.equals("")) {
            //业务逻辑的请求码，对应哪里查询做什么操作
            //判断请求码，是则进行业务操作
            String str;

        }
    }

    private OnPrintStateChangeListener mListener;

    public void setOnPrintStateChangeListener(OnPrintStateChangeListener listener) {
        this.mListener = listener;
    }

    public interface OnPrintStateChangeListener {

        void stateOn(String string);

        void stateOff(String string);

        void stateListener(String string);
    }


    //打印票据
    public void sendBillRecepit(final int number) {
        short temp = 0;
        if (ZShrPrefencs.getInstance().getNetOn() == 0) {
            temp = 14;
        } else {
            temp = 12;
        }
        int loop = 1;
        boolean isCut = true;
        int xxxx = ZShrPrefencs.getInstance().getCopyCount();
        switch (xxxx) {
            case 0:
                loop = 1;
                isCut = true;
                break;
            case 1:
                loop = 1;
                isCut = true;
                break;
            case 2:
                loop = 2;
                isCut = true;
                break;
            case 3:
                loop = 3;
                isCut = false;
                break;
            case 4:
                loop = 3;
                isCut = true;
                break;
        }
        if (ZShrPrefencs.getInstance().getNetOn() == 0) {
            final EscCommand esc = new EscCommand();
            esc.addInitializePrinter();
            for (int i = 0; i < loop; i++) {
                String fuckOff = printEntity.getTotal_freight_receipts();
                String otherFee = "";
                String mutiFee = "";
                if (otherFee != null) {
                    try {
                        String[] shit = fuckOff.split("&&");
                        otherFee = shit[0];
                        mutiFee = shit[1];
                    } catch (Exception e) {
                        Log.e("TAG", "shit is happened");
                    }
                }

                if (ZShrPrefencs.getInstance().getBigNumber() == 1) {
                    esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                    String aa = StringUtils.getNullablString(printEntity.getArticle_number());
                    if (aa != null) {
                        esc.addText(aa.substring(4, aa.length()));
                        esc.addPrintAndLineFeed();
                    }
                }

                esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);

                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                if (!isSign) {
                    esc.addText(String.format("%s货运单(" + (i + 1) + ")联", AppUserToken.getInstance().getResult().getCompany_name()));
                } else {
                    esc.addText(String.format("%s签收单", AppUserToken.getInstance().getResult().getCompany_name()));
                }

                esc.addPrintAndLineFeed();
                Date date = null;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA).parse(printEntity.getInvoice_date());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String aa = date.getHours() + "" + (String.valueOf(date.getMinutes()).length() == 1 ? "0" + String.valueOf(date.getMinutes()) : String.valueOf(date.getMinutes()));
                int bb = Integer.parseInt(aa);
                int cc = ZShrPrefencs.getInstance().getTimeDistr();
                String ban = "";
                if (cc != 0) {
                    if (bb <= cc) {
                        ban = "(中班)";
                    } else {
                        ban = "(晚班)";
                    }
                }
                if (printEntity.getTransit_destination() != null) {
                    esc.addText(printEntity.getStartStation() + "—" + StringUtils.getNullablString(printEntity.getTransit_destination()) + ban + "\n");
                } else {
                    esc.addText(printEntity.getStartStation() + "—" + StringUtils.getNullablString(printEntity.getEndStation()) + ban + "\n");
                }
                esc.addPrintAndLineFeed();

                if (ZShrPrefencs.getInstance().getHintParams() != 0 && i == 0) {
                    esc.addText("*凭此联兑代收*");
                    esc.addPrintAndLineFeed();
                    esc.addPrintAndLineFeed();
                }

                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                if (printEntity.getInvoice_date() != null) {
                    if (buda == 9999) {
                        esc.addText(String.format("开票日期: %s", StringUtils.getNullablString(printEntity.getInvoice_date())) + "(补)");
                    } else {
                        esc.addText(String.format("开票日期: %s", StringUtils.getNullablString(printEntity.getInvoice_date())));
                    }
                } else {
                    esc.addText("开票日期: ");
                }
                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                esc.addSetAbsolutePrintPosition((short) 14);
                esc.addText(String.format("开票: %s", StringUtils.getNullablString(printEntity.getUser_name())));
                esc.addPrintAndLineFeed();
//
//            esc.addText(String.format("运单号: %s", StringUtils.getNullablString(printEntity.getWaybill_number())));
//            esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
//            esc.addSetAbsolutePrintPosition((short) 14);
//
//            esc.addPrintAndLineFeed();

//            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
//            esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
//            if (printEntity.getInput_article_number() != null) {
//                esc.addText("货号: " + StringUtils.getNullablString(printEntity.getArticle_number()
//                        + "(" + printEntity.getInput_article_number() + ")"));
//            } else {
//                esc.addText("货号: " + StringUtils.getNullablString(printEntity.getArticle_number()));
//            }

                esc.addText("货号: " + StringUtils.getNullablString(printEntity.getArticle_number()) + " 自录货号: " + StringUtils.getNullablString(printEntity.getInput_article_number()));

                esc.addPrintAndLineFeed();
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);

                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();


                String getterName = printEntity.getConsignee();
                String getterPhone = printEntity.getConsignee_phone();
                int reeee = ZShrPrefencs.getInstance().getReciveSize();

                if (getterName == null || getterName.equals("")) {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    esc.addText("未填写收货人姓名");
                } else {
                    if (reeee == 0) {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                    } else {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    }
                    esc.addText(String.format("收货人: %s", getterName));
                }
                if (reeee == 0) {
                    esc.addPrintAndLineFeed();
                } else {
                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                    esc.addSetAbsolutePrintPosition((short) temp);
                }
                if (getterPhone == null || getterPhone.equals("")) {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    esc.addText("未填写收货人电话");
                } else {
                    if (reeee == 0) {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                    } else {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    }
                    esc.addText(String.format("电话: %s", getterPhone));
                }


                esc.addPrintAndLineFeed();
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();

                String senderName = printEntity.getConsigner();
                String senderPhone = printEntity.getConsigner_phone();

                int sendddd = ZShrPrefencs.getInstance().getSendSize();

                if (senderName == null || senderName.equals("")) {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    esc.addText("未填写发货人姓名");
                } else {
                    if (sendddd == 0) {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                    } else {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    }
                    esc.addText(String.format("发货人: %s", senderName));
                }

                if (sendddd == 0) {
                    esc.addPrintAndLineFeed();
                } else {
                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                    esc.addSetAbsolutePrintPosition((short) temp);
                }
                if (senderPhone == null || senderPhone.equals("")) {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    esc.addText("未填写发货人电话");
                } else {
                    if (sendddd == 0) {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                    } else {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    }
                    esc.addText(String.format("电话: %s", senderPhone));
                }

//                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
//                esc.addText(String.format("发货人: %s", StringUtils.getNullablString(printEntity.getConsigner())));
//                esc.addPrintAndLineFeed();
//                esc.addText(String.format("电话: %s", StringUtils.getNullablString(printEntity.getConsigner_phone())));
                esc.addPrintAndLineFeed();
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();

                int numberrrr = ZShrPrefencs.getInstance().getNumberSize();
                short bbb = 12;
                if (numberrrr == 0) {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                    bbb = 12;
                } else {
                    bb = temp;
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                }
                esc.addText("数量: " + printEntity.getQuantity() + "件");
                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                esc.addSetAbsolutePrintPosition((short) bb);
                esc.addText("[" + printEntity.getDelivery_method() + "]");


                esc.addPrintAndLineFeed();
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);

                esc.addText(String.format("货物名称: %s", StringUtils.getNullablString(printEntity.getGoods_name())));
                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                esc.addSetAbsolutePrintPosition((short) temp);
                esc.addText(String.format("包装方式: %s", StringUtils.getNullablString(printEntity.getPackaging())));
                esc.addPrintAndLineFeed();

                esc.addText("------------------------------------------------");

                String shitON = "";
                if (ZShrPrefencs.getInstance().getPriceDetail() == 1) {
                    shitON = "运费合计: ";
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    esc.addPrintAndLineFeed();
                    if (printEntity.getFreight() == null || printEntity.getFreight() == 0.0) {
                        esc.addText("运费: 0元");
                    } else {
                        esc.addText(String.format("运费: %s元", ZDoubleFormat.zFormat(printEntity.getFreight().toString())));
                    }
                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                    esc.addSetAbsolutePrintPosition((short) temp);

                    if (printEntity.getAdvance() == null || printEntity.getAdvance() == 0.0) {
                        esc.addText("垫付款: " + "0元");
                    } else {
                        esc.addText(String.format("垫付款: %s元", ZDoubleFormat.zFormat(printEntity.getAdvance().toString())));
                    }
                    esc.addPrintAndLineFeed();

                    if (printEntity.getDelivery_fee() == null || printEntity.getDelivery_fee() == 0.0) {
                        esc.addText("送货费: " + "0元");
                    } else {
                        esc.addText(String.format("送货费: %s元", ZDoubleFormat.zFormat(printEntity.getDelivery_fee().toString())));
                    }
                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                    esc.addSetAbsolutePrintPosition((short) temp);
                    if (printEntity.getValuation_fee() == null || printEntity.getValuation_fee() == 0.0) {
                        esc.addText("保价费: " + "0元");
                    } else {
                        esc.addText(String.format("保价费: %s元", ZDoubleFormat.zFormat(printEntity.getValuation_fee().toString())));
                    }
                    esc.addPrintAndLineFeed();

                    esc.addText("其它费用: " + otherFee);
//                    esc.addPrintAndLineFeed();
                } else {
                    shitON = "运费: ";
                }

                if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                    esc.addSetAbsolutePrintPosition((short) temp);
                } else {
                    esc.addPrintAndLineFeed();
                }

                if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                } else {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                }


                if (printEntity.getCollection_fee() == null || printEntity.getCollection_fee().equals("")) {
                    if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                        esc.addText("代收: 0元");
                    } else {
                        esc.addText("代收: ");
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 6);
                        esc.addText("0元");
                        if (ZShrPrefencs.getInstance().getBigDianFu() == 1) {
                            esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                            esc.addSetAbsolutePrintPosition((short) 12);
                            esc.addText("垫付: " + ((printEntity.getAdvance() == null || printEntity.getAdvance() == 0.0) ? "0元" : (ZDoubleFormat.zFormat(String.valueOf(printEntity.getAdvance())) + "元")));
                        }
                    }
                } else {
                    if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                        esc.addText("代收: " + ZDoubleFormat.zFormat(printEntity.getCollection_fee().toString()) + "元");
                    } else {
                        esc.addText("代收: ");
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 6);
                        esc.addText(ZDoubleFormat.zFormat(printEntity.getCollection_fee().toString()) + "元");
                        if (ZShrPrefencs.getInstance().getBigDianFu() == 1) {
                            esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                            esc.addSetAbsolutePrintPosition((short) 12);
                            esc.addText("垫付: " + ((printEntity.getAdvance() == null || printEntity.getAdvance() == 0.0) ? "0元" : (ZDoubleFormat.zFormat(String.valueOf(printEntity.getAdvance())) + "元")));
                        }
                    }
                }

                esc.addPrintAndLineFeed();

                if (printEntity.getTotal_freight() == null || printEntity.getTotal_freight() == 0.0) {
                    if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                        if (printEntity.getPayment_method() != null && printEntity.getPayment_method().equals("多笔付")) {
                            esc.addText(shitON + "0元[" + mutiFee + "]");
                        } else {
                            esc.addText(shitON + "0元[" + printEntity.getPayment_method() + "]");
                        }
                    } else {
                        esc.addText(shitON);
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 6);
                        esc.addText("0元");
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 12);
                        if (printEntity.getPayment_method() != null && printEntity.getPayment_method().equals("多笔付")) {
                            esc.addText("[" + mutiFee + "]");
                        } else {
                            esc.addText("[" + printEntity.getPayment_method() + "]");
                        }
                    }
                } else {
                    if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                        if (printEntity.getPayment_method() != null && printEntity.getPayment_method().equals("多笔付")) {
                            esc.addText(String.format(shitON + "%s元[" + mutiFee + "]", ZDoubleFormat.zFormat(printEntity.getTotal_freight().toString())));
                        } else {
                            esc.addText(String.format(shitON + "%s元[" + printEntity.getPayment_method() + "]", ZDoubleFormat.zFormat(printEntity.getTotal_freight().toString())));
                        }
                    } else {
                        esc.addText(String.format(shitON));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 6);

                        double advance = (printEntity.getAdvance() == null ? 0.0 : printEntity.getAdvance());

                        Double total_freight = printEntity.getTotal_freight();
                        if (total_freight == null) {
                            total_freight = 0.0;
                        }
                        if (ZShrPrefencs.getInstance().getBigDianFu() == 1) {
                            total_freight = total_freight - advance;
                        }

                        esc.addText(String.format("%s元", ZDoubleFormat.zFormat(total_freight.toString())));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 12);
                        if (printEntity.getPayment_method() != null && printEntity.getPayment_method().equals("多笔付")) {
                            esc.addText("[" + mutiFee + "]");
                        } else {
                            esc.addText("[" + printEntity.getPayment_method() + "]");
                        }
                    }
                }
//                if (isSign) {
//                    Double totalFee = StringUtils.getDoubleNotNull(printEntity.getFreight())
//                            + StringUtils.getDoubleNotNull(printEntity.getReceiving_fee())
//                            + StringUtils.getDoubleNotNull(printEntity.getHandling_fee())
//                            + StringUtils.getDoubleNotNull(printEntity.getForklift_fee())
//                            + StringUtils.getDoubleNotNull(printEntity.getPacking_fee())
//                            + StringUtils.getDoubleNotNull(printEntity.getUpstair_fee())
//                            + StringUtils.getDoubleNotNull(printEntity.getAdvance())
//                            + StringUtils.getDoubleNotNull(printEntity.getDelivery_fee())
//                            + StringUtils.getDoubleNotNull(printEntity.getValuation_fee())
//                            + StringUtils.getDoubleNotNull(printEntity.getCollection_fee())
//                            - StringUtils.getDoubleNotNull(printEntity.getCash_payment());
//                    esc.addText("应收费用: " + ZDoubleFormat.zFormat(totalFee.toString()) + "元");
//                    esc.addPrintAndLineFeed();
//                }

                esc.addPrintAndLineFeed();
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();

                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);

                Double total = 0.0;
                Double daishou = 0.0;
                double allMoney = 0.0;
                if (printEntity.getTotal_freight() != null) {
                    total = printEntity.getTotal_freight();
                }
                if (printEntity.getCollection_fee() != null) {
                    daishou = printEntity.getCollection_fee();
                }

                if (printEntity.getPayment_method() != null) {
                    if (printEntity.getPayment_method().equals("到付")) {
                        allMoney = total + daishou;
                    } else if (printEntity.getPayment_method().equals("现付")) {
                        allMoney = daishou;
                    } else if (printEntity.getPayment_method().equals("多笔付")) {
                        Double collect_payment = printEntity.getCollect_payment();
                        int tempx = 0;
                        if (collect_payment == null) {
                            tempx = 0;
                        } else {
                            tempx = Integer.parseInt(ZDoubleFormat.zFormat(String.valueOf(collect_payment)));
                        }
                        allMoney = tempx + daishou;
                    }
                }

                if (!isSign) {
                    esc.addText("     合计: " + ZDoubleFormat.zFormat(allMoney + "") + "元");
                } else {
                    esc.addText("     应收: " + ZDoubleFormat.zFormat(allMoney + "") + "元");
                }

                esc.addPrintAndLineFeed();
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();

                if (printEntity.getBarCode() != null) {
                    if (ZShrPrefencs.getInstance().getIsBarCode() == 1) {
                        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
                        esc.addSelectPrintingPositionForHRICharacters(EscCommand.HRI_POSITION.BELOW);
                        // 设置条码可识别字符位置在条码下方
                        // 设置条码高度为60点
                        esc.addSetBarcodeHeight((byte) 80);
                        // 设置条码单元宽度为1
                        esc.addSetBarcodeWidth((byte) 2);
                        // 打印Code128码
                        String barCode = printEntity.getBarCode();
                        if (i == 0) {
                            barCode = "99" + barCode;
                        }
                        esc.addCODE128(esc.genCode128(StringUtils.getNullablString(barCode)));
                        esc.addPrintAndLineFeed();
                    }
                }

//                esc.addText("------------------------------------------------");
                if (printEntity.getRemarks() != null && !printEntity.getRemarks().equals("")) {
                    esc.addText("备注: " + printEntity.getRemarks());
                    esc.addPrintAndLineFeed();
                }

                if (isSign) {
                    esc.addText("签收人: ");
                    esc.addPrintAndLineFeed();
                    esc.addPrintAndLineFeed();
                }

                int space = ZShrPrefencs.getInstance().getSpaceCount();

                for (int p = 0; p < space; p++) {
                    esc.addPrintAndLineFeed();
                }

                String notice = printEntity.getLogistics_notice();
                if (!isSign) {
                    if (notice != null && !notice.isEmpty()) {
                        esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                        esc.addText("声明");
                        esc.addPrintAndLineFeed();
                        esc.addText(notice);
                    }
                }
                if (AppUserToken.getInstance().getResult().getLogistics_uuid() == 2306) {
                    esc.addPrintAndLineFeed();
                    esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                    esc.addText("单子有效期3个月");
                }
//            List<Declaration> notices = AppUserToken.getInstance().getDeclars();
//            if (notices != null && notices.size() != 0) {
//                for (int k = 0; k < notices.size(); k++) {
//                    esc.addText((k + 1) + "," + notices.get(k).getLogistics_notice());
//                    esc.addPrintAndLineFeed();
//                }
//            } else {
//                esc.addText("1,货主通过平台直收货款;承运公司确保传统代收货款安全。");
//                esc.addPrintAndLineFeed();
//                esc.addText("2,保价费按实际货物价值缴纳(特殊货品除外)。如未保价，按最高不超过运费10倍赔偿，易碎品货损的6%属正常损耗。");
//                esc.addPrintAndLineFeed();
//                esc.addText("3,危险品、禁运品不予运输。虚报货名，后果自负。");
//                esc.addPrintAndLineFeed();
//            }
                esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                esc.addPrintAndLineFeed();
                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();

                String pointName = AppUserToken.getInstance().getResult().getPoint_name();
                String pointPhone = printEntity.getStartPoint_phone();

                String endName = printEntity.getEndStation();
                String endPoint = printEntity.getPoint_owner_phone_o();


                esc.addText("开票网点: " + StringUtils.getNullablString(pointName));
                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                esc.addSetAbsolutePrintPosition((short) 10);

                if (pointPhone != null) {
                    String tempaaa = pointPhone.split(",")[0];
                    esc.addText("查货电话: " + tempaaa);
                }
                esc.addPrintAndLineFeed();
                if (pointPhone != null) {
                    if (pointPhone.split(",").length == 2) {
                        esc.addSelectJustification(EscCommand.JUSTIFICATION.RIGHT);
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                        esc.addText(pointPhone.split(",")[1]);
                        esc.addPrintAndLineFeed();
                    }
                }
                esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();
                esc.addText("提货网点: " + StringUtils.getNullablString(endName));
                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                esc.addSetAbsolutePrintPosition((short) 10);

                if (endPoint != null) {
                    String tempxx = endPoint.split(",")[0];
                    esc.addText("提货电话: " + tempxx);
                    esc.addPrintAndLineFeed();
                }

                if (endPoint != null) {
                    if (endPoint.split(",").length == 2) {
                        esc.addSelectJustification(EscCommand.JUSTIFICATION.RIGHT);
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                        esc.addText(pointPhone.split(",")[1]);
                        esc.addPrintAndLineFeed();
                    }
                }
                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();

                esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
                esc.addText(" 骏诚勤牛供应链监制" + "  www.chinniu.com");
                esc.addPrintAndLineFeed();
//                esc.addGeneratePlus(LabelCommand.FOOT.F2, (byte) 255, (byte) 255);
                esc.addPrintAndFeedLines((byte) 3);
                esc.addPrintAndFeedLines((byte) 1);

                if (loop != 1 && i != loop - 1) {
                    if (xxxx != 3) {
                        esc.addCutPaper();
                    } else {
                        if (i != 1) {
                            esc.addCutPaper();
                        }
                    }
                }
            }
            //for 循环结束
            esc.addCutPaper();
            esc.addQueryPrinterStatus();
            Vector<Byte> datas = esc.getCommand();//发送数据
            DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].sendDataImmediately(datas);
        } else {
            MainActivity.Companion.getBinder().writeDataByYouself(
                    new UiExecute() {
                        @Override
                        public void onsucess() {
                            MainActivity.Companion.getBinder().disconnectCurrentPort(new UiExecute() {
                                @Override
                                public void onsucess() {
                                    Log.e("TAG", "打印完毕，断开成功");
                                }

                                @Override
                                public void onfailed() {
                                    Log.e("TAG", "打印完毕，断开失败");
                                }
                            });

                        }

                        @Override
                        public void onfailed() {

                        }
                    }, new ProcessData() {
                        @Override
                        public List<byte[]> processDataBeforeSend() {
                            List<byte[]> list = new ArrayList<byte[]>();
                            list.add(DataForSendToPrinterPos80.initializePrinter());
                            int loop = 1;
                            boolean isCut = true;
                            int xxxx = ZShrPrefencs.getInstance().getCopyCount();
                            switch (xxxx) {
                                case 0:
                                    loop = 1;
                                    isCut = true;
                                    break;
                                case 1:
                                    loop = 1;
                                    isCut = true;
                                    break;
                                case 2:
                                    loop = 2;
                                    isCut = true;
                                    break;
                                case 3:
                                    loop = 3;
                                    isCut = false;
                                    break;
                                case 4:
                                    loop = 3;
                                    isCut = true;
                                    break;
                            }
                            for (int i = 0; i < loop; i++) {
                                final EscCommand esc = new EscCommand();
                                String fuckOff = printEntity.getTotal_freight_receipts();
                                String otherFee = "";
                                String mutiFee = "";
                                if (otherFee != null) {
                                    try {
                                        String[] shit = fuckOff.split("&&");
                                        otherFee = shit[0];
                                        mutiFee = shit[1];
                                    } catch (Exception e) {
                                        Log.e("TAG", "shit is happened");
                                    }
                                }

                                if (ZShrPrefencs.getInstance().getBigNumber() == 1) {
                                    esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                                    String aa = StringUtils.getNullablString(printEntity.getArticle_number());
                                    if (aa != null) {
                                        esc.addText(aa.substring(4, aa.length()));
                                        esc.addPrintAndLineFeed();
                                    }
                                }

                                esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
                                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                                if (!isSign) {
                                    esc.addText(String.format("%s货运单(" + (i + 1) + ")联", AppUserToken.getInstance().getResult().getCompany_name()));
                                } else {
                                    esc.addText(String.format("%s签收单", AppUserToken.getInstance().getResult().getCompany_name()));
                                }

                                esc.addPrintAndLineFeed();
                                Date date = null;
                                try {
                                    date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA).parse(printEntity.getInvoice_date());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                String aa = date.getHours() + "" + (String.valueOf(date.getMinutes()).length() == 1 ? "0" + String.valueOf(date.getMinutes()) : String.valueOf(date.getMinutes()));

                                int bb = Integer.parseInt(aa);
                                int cc = ZShrPrefencs.getInstance().getTimeDistr();
                                String ban = "";
                                if (cc != 0) {
                                    if (bb <= cc) {
                                        ban = "(中班)";
                                    } else {
                                        ban = "(晚班)";
                                    }
                                }
                                if (printEntity.getTransit_destination() != null) {
                                    esc.addText(printEntity.getStartStation() + "—" + StringUtils.getNullablString(printEntity.getTransit_destination()) + ban + "\n");
                                } else {
                                    esc.addText(printEntity.getStartStation() + "—" + StringUtils.getNullablString(printEntity.getEndStation()) + ban + "\n");
                                }
                                esc.addPrintAndLineFeed();

                                if (ZShrPrefencs.getInstance().getHintParams() != 0 && i == 0) {
                                    esc.addText("*凭此联兑代收*");
                                    esc.addPrintAndLineFeed();
                                    esc.addPrintAndLineFeed();
                                }

                                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                                if (printEntity.getInvoice_date() != null) {
                                    if (buda == 9999) {
                                        esc.addText(String.format("开票日期: %s", StringUtils.getNullablString(printEntity.getInvoice_date())) + "(补)");
                                    } else {
                                        esc.addText(String.format("开票日期: %s", StringUtils.getNullablString(printEntity.getInvoice_date())));
                                    }
                                } else {
                                    esc.addText("开票日期: ");
                                }
                                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                esc.addSetAbsolutePrintPosition((short) 12);
                                esc.addText(String.format("开票: %s", StringUtils.getNullablString(printEntity.getUser_name())));
                                esc.addPrintAndLineFeed();
//
//            esc.addText(String.format("运单号: %s", StringUtils.getNullablString(printEntity.getWaybill_number())));
//            esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
//            esc.addSetAbsolutePrintPosition((short) 14);
//
//            esc.addPrintAndLineFeed();

//            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
//            esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
//            if (printEntity.getInput_article_number() != null) {
//                esc.addText("货号: " + StringUtils.getNullablString(printEntity.getArticle_number()
//                        + "(" + printEntity.getInput_article_number() + ")"));
//            } else {
//                esc.addText("货号: " + StringUtils.getNullablString(printEntity.getArticle_number()));
//            }

                                esc.addText("货号: " + StringUtils.getNullablString(printEntity.getArticle_number()) + "自录货号: " + StringUtils.getNullablString(printEntity.getInput_article_number()));

                                esc.addPrintAndLineFeed();
                                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);

                                esc.addText("------------------------------------------------");
                                esc.addPrintAndLineFeed();


                                String getterName = printEntity.getConsignee();
                                String getterPhone = printEntity.getConsignee_phone();

                                int reeee = ZShrPrefencs.getInstance().getReciveSize();
                                if (getterName == null || getterName.equals("")) {
                                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                    esc.addText("未填写收货人姓名");
                                } else {
                                    if (reeee == 0) {
                                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                                    } else {
                                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                    }
                                    esc.addText(String.format("收货人: %s", getterName));
                                }
                                if (reeee == 0) {
                                    esc.addPrintAndLineFeed();
                                } else {
                                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                    esc.addSetAbsolutePrintPosition((short) 12);
                                }
                                if (getterPhone == null || getterPhone.equals("")) {
                                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                    esc.addText("未填写收货人电话");
                                } else {
                                    if (reeee == 0) {
                                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                                    } else {
                                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                    }
                                    esc.addText(String.format("电话: %s", getterPhone));
                                }


                                esc.addPrintAndLineFeed();

                                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                esc.addText("------------------------------------------------");
                                esc.addPrintAndLineFeed();

                                String senderName = printEntity.getConsigner();
                                String senderPhone = printEntity.getConsigner_phone();

                                int sendddd = ZShrPrefencs.getInstance().getSendSize();
                                if (senderName == null || senderName.equals("")) {
                                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                    esc.addText("未填写发货人姓名");
                                } else {
                                    if (sendddd == 0) {
                                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                                    } else {
                                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                    }
                                    esc.addText(String.format("发货人: %s", senderName));
                                }

                                if (sendddd == 0) {
                                    esc.addPrintAndLineFeed();
                                } else {
                                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                    esc.addSetAbsolutePrintPosition((short) 12);
                                }
                                if (senderPhone == null || senderPhone.equals("")) {
                                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                    esc.addText("未填写发货人电话");
                                } else {
                                    if (sendddd == 0) {
                                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                                    } else {
                                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                    }
                                    esc.addText(String.format("电话: %s", senderPhone));
                                }

                                esc.addPrintAndLineFeed();
                                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                esc.addText("------------------------------------------------");
                                esc.addPrintAndLineFeed();

                                int numberrrr = ZShrPrefencs.getInstance().getNumberSize();

                                if (numberrrr == 0) {
                                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                                } else {
                                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                }
                                esc.addText("数量: " + printEntity.getQuantity() + "件");
                                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                esc.addSetAbsolutePrintPosition((short) 12);
                                esc.addText("[" + printEntity.getDelivery_method() + "]");


                                esc.addPrintAndLineFeed();
                                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);

                                esc.addText(String.format("货物名称: %s", StringUtils.getNullablString(printEntity.getGoods_name())));
                                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                esc.addSetAbsolutePrintPosition((short) 12);
                                esc.addText(String.format("包装方式: %s", StringUtils.getNullablString(printEntity.getPackaging())));
                                esc.addPrintAndLineFeed();

                                esc.addText("------------------------------------------------");

                                String shitON = "";
                                if (ZShrPrefencs.getInstance().getPriceDetail() == 1) {
                                    shitON = "运费合计: ";
                                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                    esc.addPrintAndLineFeed();
                                    if (printEntity.getFreight() == null || printEntity.getFreight() == 0.0) {
                                        esc.addText("运费: 0元");
                                    } else {
                                        esc.addText(String.format("运费: %s元", ZDoubleFormat.zFormat(printEntity.getFreight().toString())));
                                    }

                                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                    esc.addSetAbsolutePrintPosition((short) 12);

                                    if (printEntity.getAdvance() == null || printEntity.getAdvance() == 0.0) {
                                        esc.addText("垫付款: " + "0元");
                                    } else {
                                        esc.addText(String.format("垫付款: %s元", ZDoubleFormat.zFormat(printEntity.getAdvance().toString())));
                                    }
                                    esc.addPrintAndLineFeed();

                                    if (printEntity.getDelivery_fee() == null || printEntity.getDelivery_fee() == 0.0) {
                                        esc.addText("送货费: " + "0元");
                                    } else {
                                        esc.addText(String.format("送货费: %s元", ZDoubleFormat.zFormat(printEntity.getDelivery_fee().toString())));
                                    }
                                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                    esc.addSetAbsolutePrintPosition((short) 12);
                                    if (printEntity.getValuation_fee() == null || printEntity.getValuation_fee() == 0.0) {
                                        esc.addText("保价费: " + "0元");
                                    } else {
                                        esc.addText(String.format("保价费: %s元", ZDoubleFormat.zFormat(printEntity.getValuation_fee().toString())));
                                    }
                                    esc.addPrintAndLineFeed();

                                    esc.addText("其它费用: " + otherFee);
//                                    esc.addPrintAndLineFeed();
                                } else {
                                    shitON = "运费: ";
                                }

                                if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                    esc.addSetAbsolutePrintPosition((short) 12);
                                } else {
                                    esc.addPrintAndLineFeed();
                                }

                                if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                } else {
                                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                                }

                                if (printEntity.getCollection_fee() == null || printEntity.getCollection_fee().equals("")) {
                                    if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                                        esc.addText("代收: 0元");
                                    } else {
                                        esc.addText("代收: ");
                                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                        esc.addSetAbsolutePrintPosition((short) 7);
                                        esc.addText("0元");
                                        if (ZShrPrefencs.getInstance().getBigDianFu() == 1) {
                                            esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                            esc.addSetAbsolutePrintPosition((short) 12);
                                            esc.addText("垫付: " + ((printEntity.getAdvance() == null || printEntity.getAdvance() == 0.0) ? "0元" : (ZDoubleFormat.zFormat(String.valueOf(printEntity.getAdvance())) + "元")));
                                        }
                                    }
                                } else {
                                    if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                                        esc.addText("代收: " + ZDoubleFormat.zFormat(printEntity.getCollection_fee().toString()) + "元");
                                    } else {
                                        esc.addText("代收: ");
                                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                        esc.addSetAbsolutePrintPosition((short) 7);
                                        esc.addText(ZDoubleFormat.zFormat(printEntity.getCollection_fee().toString()) + "元");
                                        if (ZShrPrefencs.getInstance().getBigDianFu() == 1) {
                                            esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                            esc.addSetAbsolutePrintPosition((short) 12);
                                            esc.addText("垫付: " + ((printEntity.getAdvance() == null || printEntity.getAdvance() == 0.0) ? "0元" : (ZDoubleFormat.zFormat(String.valueOf(printEntity.getAdvance())) + "元")));
                                        }
                                    }
                                }

                                esc.addPrintAndLineFeed();

                                if (printEntity.getTotal_freight() == null || printEntity.getTotal_freight() == 0.0) {
                                    if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                                        if (printEntity.getPayment_method() != null && printEntity.getPayment_method().equals("多笔付")) {
                                            esc.addText(shitON + "0元[" + mutiFee + "]");
                                        } else {
                                            esc.addText(shitON + "0元[" + printEntity.getPayment_method() + "]");
                                        }
                                    } else {
                                        esc.addText(shitON);
                                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                        esc.addSetAbsolutePrintPosition((short) 6);
                                        esc.addText("0元");
                                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                        esc.addSetAbsolutePrintPosition((short) 12);
                                        if (printEntity.getPayment_method() != null && printEntity.getPayment_method().equals("多笔付")) {
                                            esc.addText("[" + mutiFee + "]");
                                        } else {
                                            esc.addText("[" + printEntity.getPayment_method() + "]");
                                        }
                                    }
                                } else {
                                    if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                                        if (printEntity.getPayment_method() != null && printEntity.getPayment_method().equals("多笔付")) {
                                            esc.addText(String.format(shitON + "%s元[" + mutiFee + "]", ZDoubleFormat.zFormat(printEntity.getTotal_freight().toString())));
                                        } else {
                                            esc.addText(String.format(shitON + "%s元[" + printEntity.getPayment_method() + "]", ZDoubleFormat.zFormat(printEntity.getTotal_freight().toString())));
                                        }
                                    } else {
                                        esc.addText(String.format(shitON));
                                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                        esc.addSetAbsolutePrintPosition((short) 6);

                                        double advance = (printEntity.getAdvance() == null ? 0.0 : printEntity.getAdvance());

                                        Double total_freight = printEntity.getTotal_freight();
                                        if (total_freight == null) {
                                            total_freight = 0.0;
                                        }
                                        if (ZShrPrefencs.getInstance().getBigDianFu() == 1) {
                                            total_freight = total_freight - advance;
                                        }
                                        esc.addText(String.format("%s元", ZDoubleFormat.zFormat(total_freight.toString())));
                                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                        esc.addSetAbsolutePrintPosition((short) 12);
                                        if (printEntity.getPayment_method() != null && printEntity.getPayment_method().equals("多笔付")) {
                                            esc.addText("[" + mutiFee + "]");
                                        } else {
                                            esc.addText("[" + printEntity.getPayment_method() + "]");
                                        }
                                    }
                                }

//                                if (isSign) {
//                                    Double totalFee = StringUtils.getDoubleNotNull(printEntity.getFreight())
//                                            + StringUtils.getDoubleNotNull(printEntity.getReceiving_fee())
//                                            + StringUtils.getDoubleNotNull(printEntity.getHandling_fee())
//                                            + StringUtils.getDoubleNotNull(printEntity.getForklift_fee())
//                                            + StringUtils.getDoubleNotNull(printEntity.getPacking_fee())
//                                            + StringUtils.getDoubleNotNull(printEntity.getUpstair_fee())
//                                            + StringUtils.getDoubleNotNull(printEntity.getAdvance())
//                                            + StringUtils.getDoubleNotNull(printEntity.getDelivery_fee())
//                                            + StringUtils.getDoubleNotNull(printEntity.getValuation_fee())
//                                            + StringUtils.getDoubleNotNull(printEntity.getCollection_fee())
//                                            - StringUtils.getDoubleNotNull(printEntity.getCash_payment());
//                                    esc.addText("应收费用: " + ZDoubleFormat.zFormat(totalFee.toString()) + "元");
//                                    esc.addPrintAndLineFeed();
//                                }


                                esc.addPrintAndLineFeed();
                                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                esc.addText("------------------------------------------------");
                                esc.addPrintAndLineFeed();


                                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);

                                Double total = 0.0;
                                Double daishou = 0.0;
                                double allMoney = 0.0;
                                if (printEntity.getTotal_freight() != null) {
                                    total = printEntity.getTotal_freight();
                                }
                                if (printEntity.getCollection_fee() != null) {
                                    daishou = printEntity.getCollection_fee();
                                }
                                if (printEntity.getPayment_method() != null) {
                                    if (printEntity.getPayment_method().equals("到付")) {
                                        allMoney = total + daishou;
                                    } else if (printEntity.getPayment_method().equals("现付")) {
                                        allMoney = daishou;
                                    } else if (printEntity.getPayment_method().equals("多笔付")) {
                                        Double collect_payment = printEntity.getCollect_payment();
                                        int tempx = 0;
                                        if (collect_payment == null) {
                                            tempx = 0;
                                        } else {
                                            tempx = Integer.parseInt(ZDoubleFormat.zFormat(String.valueOf(collect_payment)));
                                        }
                                        allMoney = tempx + daishou;
                                    }
                                }

                                if (!isSign) {
                                    esc.addText("     合计: " + ZDoubleFormat.zFormat(allMoney + "") + "元");
                                } else {
                                    esc.addText("     应收: " + ZDoubleFormat.zFormat(allMoney + "") + "元");
                                }

                                esc.addPrintAndLineFeed();
                                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                esc.addText("------------------------------------------------");
                                esc.addPrintAndLineFeed();

                                Vector<Byte> datas = esc.getCommand();//发送数据
                                byte[] bytes = GpUtils.ByteTo_byte(datas);
                                list.add(bytes);

                                if (printEntity.getBarCode() != null) {
                                    if (ZShrPrefencs.getInstance().getIsBarCode() == 1) {
                                        list.add(DataForSendToPrinterPos80.selectHRICharacterPrintPosition(2));
//                                //设置条码宽度,参数单位和意义请参考编程手册
                                        list.add(DataForSendToPrinterPos80.selectAlignment(1));
                                        list.add(DataForSendToPrinterPos80.setBarcodeWidth(2));
                                        list.add(DataForSendToPrinterPos80.setBarcodeHeight(80));
                                        if (!printEntity.getBarCode().isEmpty()) {
                                            if (i == 0) {
                                                list.add(DataForSendToPrinterPos80.printBarcode(70, 24, "99" + printEntity.getBarCode()));
                                                list.add(DataForSendToPrinterPos80.printAndFeedLine());
                                            } else {
                                                list.add(DataForSendToPrinterPos80.printBarcode(70, 22, printEntity.getBarCode()));
                                            }
                                        }
                                        list.add(DataForSendToPrinterPos80.printAndFeedLine());
                                    }
                                }


                                EscCommand esc1 = new EscCommand();
                                if (printEntity.getRemarks() != null && !printEntity.getRemarks().equals("")) {
                                    esc1.addText("备注: " + printEntity.getRemarks());
                                    esc1.addPrintAndLineFeed();
                                }

                                int space = ZShrPrefencs.getInstance().getSpaceCount();

                                for (int p = 0; p < space; p++) {
                                    esc1.addPrintAndLineFeed();
                                }

                                if (isSign) {
                                    esc1.addText("签收人: ");
                                    esc1.addPrintAndLineFeed();
                                    esc1.addPrintAndLineFeed();
                                }

                                String notice = printEntity.getLogistics_notice();
                                if (!isSign) {
                                    if (notice != null && !notice.isEmpty()) {
                                        esc1.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
                                        esc1.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                                        esc1.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                        esc1.addText("声明");
                                        esc1.addPrintAndLineFeed();
                                        esc1.addText(notice);
                                    }
                                }
                                if (AppUserToken.getInstance().getResult().getLogistics_uuid() == 2306) {
                                    esc1.addPrintAndLineFeed();
                                    esc1.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
                                    esc1.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                                    esc1.addText("单子有效期3个月");
                                }
//            List<Declaration> notices = AppUserToken.getInstance().getDeclars();
//            if (notices != null && notices.size() != 0) {
//                for (int k = 0; k < notices.size(); k++) {
//                    esc.addText((k + 1) + "," + notices.get(k).getLogistics_notice());
//                    esc.addPrintAndLineFeed();
//                }
//            } else {
//                esc.addText("1,货主通过平台直收货款;承运公司确保传统代收货款安全。");
//                esc.addPrintAndLineFeed();
//                esc.addText("2,保价费按实际货物价值缴纳(特殊货品除外)。如未保价，按最高不超过运费10倍赔偿，易碎品货损的6%属正常损耗。");
//                esc.addPrintAndLineFeed();
//                esc.addText("3,危险品、禁运品不予运输。虚报货名，后果自负。");
//                esc.addPrintAndLineFeed();
//            }
                                esc1.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                                esc1.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                esc1.addPrintAndLineFeed();
                                esc1.addText("------------------------------------------------");
                                esc1.addPrintAndLineFeed();

                                String pointName = AppUserToken.getInstance().getResult().getPoint_name();
                                String pointPhone = printEntity.getStartPoint_phone();

                                String endName = printEntity.getEndStation();
                                String endPoint = printEntity.getPoint_owner_phone_o();


                                esc1.addText("开票网点: " + StringUtils.getNullablString(pointName));
                                esc1.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                esc1.addSetAbsolutePrintPosition((short) 10);

                                if (pointPhone != null) {
                                    String tempaaa = pointPhone.split(",")[0];
                                    esc1.addText("查货电话: " + tempaaa);
                                }
                                esc1.addPrintAndLineFeed();
                                if (pointPhone != null) {
                                    if (pointPhone.split(",").length == 2) {
                                        esc1.addSelectJustification(EscCommand.JUSTIFICATION.RIGHT);
                                        esc1.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                        esc1.addText(pointPhone.split(",")[1]);
                                        esc1.addPrintAndLineFeed();
                                    }
                                }
                                esc1.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                                esc1.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                esc1.addText("------------------------------------------------");
                                esc1.addPrintAndLineFeed();
                                esc1.addText("提货网点: " + StringUtils.getNullablString(endName));
                                esc1.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                esc1.addSetAbsolutePrintPosition((short) 10);

                                if (endPoint != null) {
                                    String temp = endPoint.split(",")[0];
                                    esc1.addText("提货电话: " + temp);
                                    esc1.addPrintAndLineFeed();
                                }

                                if (endPoint != null) {
                                    if (endPoint.split(",").length == 2) {
                                        esc1.addSelectJustification(EscCommand.JUSTIFICATION.RIGHT);
                                        esc1.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                        esc1.addText(endPoint.split(",")[1]);
                                        esc1.addPrintAndLineFeed();
                                    }
                                }

                                esc1.addText("------------------------------------------------");
                                esc1.addPrintAndLineFeed();

                                esc1.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
                                esc1.addText(" 骏诚勤牛供应链监制" + "  www.chinniu.com");
                                esc1.addPrintAndLineFeed();
//                                esc1.addGeneratePlus(LabelCommand.FOOT.F2, (byte) 255, (byte) 255);
                                esc1.addPrintAndFeedLines((byte) 3);
                                esc1.addPrintAndFeedLines((byte) 1);

                                Vector<Byte> datas1 = esc1.getCommand();//发送数据
                                byte[] bytes1 = GpUtils.ByteTo_byte(datas1);
                                list.add(bytes1);

                                if (loop != 1 && i != loop - 1) {
                                    if (xxxx != 3) {
                                        list.add(DataForSendToPrinterPos80.selectCutPagerModerAndCutPager(1));
                                    } else {
                                        if (i != 1) {
                                            list.add(DataForSendToPrinterPos80.selectCutPagerModerAndCutPager(1));
                                        }
                                    }
                                }
                            }
                            //for 循环结束
                            EscCommand esc2 = new EscCommand();
                            esc2.addCutPaper();
                            esc2.addQueryPrinterStatus();
                            Vector<Byte> datas2 = esc2.getCommand();//发送数据
                            byte[] bytes2 = GpUtils.ByteTo_byte(datas2);
                            list.add(bytes2);
                            return list;
                        }
                    });

        }

    }

    public void sendPosRecepit(final BillingDrawMain printEntity) {
        MainActivity.Companion.getBinderX().writeDataByYouself(new UiExecute() {
            @Override
            public void onsucess() {
                Log.e("TAG", "xy打印成功");
                MainActivity.Companion.getBinderX().disconnectCurrentPort(new UiExecute() {
                    @Override
                    public void onsucess() {
                        Log.e("TAG", "标签打印完毕，断开成功");
                    }

                    @Override
                    public void onfailed() {
                        Log.e("TAG", "标签打印完毕，断开失败");
                    }
                });
            }

            @Override
            public void onfailed() {
                Log.e("TAG", "xy打印失败");
            }
        }, new ProcessData() {
            @Override
            public List<byte[]> processDataBeforeSend() {
                long count = printEntity.getQuantity();

                int max = ZShrPrefencs.getInstance().getPosMax();

                if (max != 0) {
                    if (count >= max) {
                        count = max;
                    }
                }

                ArrayList<byte[]> list = new ArrayList<byte[]>();
                for (int i = 0; i < count; i++) {
                    //在打印请可以先设置打印内容的字符编码类型，默认为gbk，请选择打印机可识别的类型，参看编程手册，打印代码页
                    DataForSendToPrinterTSC.setCharsetName("gbk");
                    //不设置，默认为gbk
                    //通过工具类得到一个指令的byte[]数据,以文本为例
                    //首先得设置size标签尺寸,宽60mm,高30mm,也可以调用以dot或inch为单位的方法具体换算参考编程手册
                    byte[] data = DataForSendToPrinterTSC.sizeBymm(78, 42);
                    list.add(data);
                    //设置Gap,同上
                    list.add(DataForSendToPrinterTSC.gapBymm(0, 0));
                    //清除缓存
                    list.add(DataForSendToPrinterTSC.cls());
                    list.add(DataForSendToPrinterTSC.text(20, 30, "TSS24.BF2", 0, 1, 1, AppUserToken.getInstance().getResult().getCompany_name()));
                    list.add(DataForSendToPrinterTSC.text(430, 30, "TSS24.BF2", 0, 1, 1, "第 " + (i + 1) + " 件"));
//                    String station = printEntity.getStartStation() + printEntity.getArticle_number()
//                            .substring(printEntity.getArticle_number().length() - 6, printEntity.getArticle_number().length());
//                    String startStation = StringUtils.getNullablString(printEntity.getStartStation());
//                    String endStation = StringUtils.getNullablString(printEntity.getEndStation());

                    String station = "";

                    if (printEntity.getTransit_destination() != null) {
                        station = printEntity.getTransit_destination();
                    } else {
                        station = printEntity.getEndStation();
                    }

                    String temp = printEntity.getStartStation() + printEntity.getArticle_number()
                            .substring(printEntity.getArticle_number().length() - 6, printEntity.getArticle_number().length()) + station;

                    int length = temp.length();
                    int x = 0;
                    switch (length) {
                        case 12:
                            x = 80;
                            break;
                        case 11:
                            x = 100;
                            break;
                        case 10:
                            x = 130;
                            break;
                        default:
                            x = 80;
                            break;
                    }

                    list.add(DataForSendToPrinterTSC.text(x, 70, "TSS24.BF2", 0, 2, 2, temp));

//                    list.add(DataForSendToPrinterTSC.text(20, 135, "TSS24.BF2", 0, 1, 1, StringUtils.getNullablString(printEntity.getConsignee()) + " 收"));
                    list.add(DataForSendToPrinterTSC.text(400, 135, "TSS24.BF2", 0, 1, 1, "货物: " + StringUtils.getNullablString(printEntity.getGoods_name())));

//                    list.add(DataForSendToPrinterTSC.barCode(
//                            135, 165, "128", 90, 1, 0, 2, 2,
//                            printEntity.getBarCode()));

                    list.add(DataForSendToPrinterTSC.text(80, 175, "TSS24.BF2", 0, 3, 3, StringUtils.getNullablString(printEntity.getConsignee()) + " 收"));


                    list.add(DataForSendToPrinterTSC.text(20, 285, "TSS24.BF2", 0, 1, 1, "www.chinniu.com 监制"));
                    list.add(DataForSendToPrinterTSC.text(400, 285, "TSS24.BF2", 0, 1, 1, printEntity.getInvoice_date()));

                    //打印
                    list.add(DataForSendToPrinterTSC.print(1));
                }
                return list;
            }
        });
    }

    public void sendPosRecepitSimple(final BillingDrawMain printEntity) {
        MainActivity.Companion.getBinderX().writeDataByYouself(new UiExecute() {
            @Override
            public void onsucess() {
                Log.e("TAG", "xy打印成功");
                MainActivity.Companion.getBinderX().disconnectCurrentPort(new UiExecute() {
                    @Override
                    public void onsucess() {
                        Log.e("TAG", "标签打印完毕，断开成功");
                    }

                    @Override
                    public void onfailed() {
                        Log.e("TAG", "标签打印完毕，断开失败");
                    }
                });
            }

            @Override
            public void onfailed() {
                Log.e("TAG", "xy打印失败");
            }
        }, new ProcessData() {
            @Override
            public List<byte[]> processDataBeforeSend() {
                long count = printEntity.getQuantity();
                int max = ZShrPrefencs.getInstance().getPosMax();
                if (max != 0) {
                    if (count >= max) {
                        count = max;
                    }
                }
                ArrayList<byte[]> list = new ArrayList<byte[]>();
                for (int i = 0; i < count; i++) {
                    //在打印请可以先设置打印内容的字符编码类型，默认为gbk，请选择打印机可识别的类型，参看编程手册，打印代码页
                    DataForSendToPrinterTSC.setCharsetName("gbk");
                    //不设置，默认为gbk
                    //通过工具类得到一个指令的byte[]数据,以文本为例
                    //首先得设置size标签尺寸,宽60mm,高30mm,也可以调用以dot或inch为单位的方法具体换算参考编程手册
                    byte[] data = DataForSendToPrinterTSC.sizeBymm(78, 42);
                    list.add(data);
                    //设置Gap,同上
                    list.add(DataForSendToPrinterTSC.gapBymm(0, 0));
                    //清除 缓存
                    list.add(DataForSendToPrinterTSC.cls());
                    list.add(DataForSendToPrinterTSC.text(20, 30, "TSS24.BF2", 0, 1, 1, AppUserToken.getInstance().getResult().getCompany_name()));

                    String station = "";

                    if (printEntity.getTransit_destination() != null) {
                        station = printEntity.getTransit_destination();
                    } else {
                        station = printEntity.getEndStation();
                    }

                    String temp = printEntity.getArticle_number()
                            .substring(printEntity.getArticle_number().length() - 6, printEntity.getArticle_number().length());

                    list.add(DataForSendToPrinterTSC.text(220, 30, "TSS24.BF2", 0, 3, 3, station));
                    list.add(DataForSendToPrinterTSC.text(470, 30, "TSS24.BF2", 0, 1, 1, "第 " + (i + 1) + " 件"));
                    list.add(DataForSendToPrinterTSC.text(180, 140, "TSS24.BF2", 0, 3, 3, temp));
                    list.add(DataForSendToPrinterTSC.text(400, 180, "TSS24.BF2", 0, 2, 2, StringUtils.getNullablString(printEntity.getConsignee())));
                    list.add(DataForSendToPrinterTSC.text(20, 285, "TSS24.BF2", 0, 1, 1, "www.chinniu.com 监制"));
                    list.add(DataForSendToPrinterTSC.text(400, 285, "TSS24.BF2", 0, 1, 1, printEntity.getInvoice_date()));

                    //打印
                    list.add(DataForSendToPrinterTSC.print(1));
                }
                return list;
            }
        });
    }

    public void sendPosRecepitBigCompany(final BillingDrawMain printEntity) {
        MainActivity.Companion.getBinderX().writeDataByYouself(new UiExecute() {
            @Override
            public void onsucess() {
                Log.e("TAG", "xy打印成功");
                MainActivity.Companion.getBinderX().disconnectCurrentPort(new UiExecute() {
                    @Override
                    public void onsucess() {
                        Log.e("TAG", "标签打印完毕，断开成功");
                    }

                    @Override
                    public void onfailed() {
                        Log.e("TAG", "标签打印完毕，断开失败");
                    }
                });
            }

            @Override
            public void onfailed() {
                Log.e("TAG", "xy打印失败");
            }
        }, new ProcessData() {
            @Override
            public List<byte[]> processDataBeforeSend() {
                long count = printEntity.getQuantity();

                int max = ZShrPrefencs.getInstance().getPosMax();

                if (max != 0) {
                    if (count >= max) {
                        count = max;
                    }
                }

                ArrayList<byte[]> list = new ArrayList<byte[]>();
                for (int i = 0; i < count; i++) {
                    //在打印请可以先设置打印内容的字符编码类型，默认为gbk，请选择打印机可识别的类型，参看编程手册，打印代码页
                    DataForSendToPrinterTSC.setCharsetName("gbk");
                    //不设置，默认为gbk
                    //通过工具类得到一个指令的byte[]数据,以文本为例
                    //首先得设置size标签尺寸,宽60mm,高30mm,也可以调用以dot或inch为单位的方法具体换算参考编程手册
                    byte[] data = DataForSendToPrinterTSC.sizeBymm(78, 42);
                    list.add(data);
                    //设置Gap,同上
                    list.add(DataForSendToPrinterTSC.gapBymm(0, 0));
                    //清除缓存
                    list.add(DataForSendToPrinterTSC.cls());
                    list.add(DataForSendToPrinterTSC.text(20, 35, "TSS24.BF2", 0, 2, 2, AppUserToken.getInstance().getResult().getCompany_name()));
                    list.add(DataForSendToPrinterTSC.text(430, 30, "TSS24.BF2", 0, 1, 1, "第 " + (i + 1) + " 件"));

                    String station = "";

                    if (printEntity.getTransit_destination() != null) {
                        station = printEntity.getTransit_destination();
                    } else {
                        station = printEntity.getEndStation();
                    }

                    String temp = printEntity.getStartStation() + printEntity.getArticle_number()
                            .substring(printEntity.getArticle_number().length() - 6, printEntity.getArticle_number().length()) + station;

                    int length = temp.length();
                    int x = 0;
                    switch (length) {
                        case 12:
                            x = 80;
                            break;
                        case 11:
                            x = 100;
                            break;
                        case 10:
                            x = 130;
                            break;
                        default:
                            x = 80;
                            break;
                    }

                    list.add(DataForSendToPrinterTSC.text(x, 90, "TSS24.BF2", 0, 2, 2, temp));

                    list.add(DataForSendToPrinterTSC.text(80, 175, "TSS24.BF2", 0, 3, 3, StringUtils.getNullablString(printEntity.getConsignee()) + " 收"));

                    list.add(DataForSendToPrinterTSC.text(20, 285, "TSS24.BF2", 0, 1, 1, "www.chinniu.com 监制"));
                    list.add(DataForSendToPrinterTSC.text(400, 285, "TSS24.BF2", 0, 1, 1, printEntity.getInvoice_date()));

                    //打印
                    list.add(DataForSendToPrinterTSC.print(1));
                }
                return list;
            }
        });
    }

    public void sendPosBB(final BillingDrawMain printEntity) {
        boolean connState = DeviceConnFactoryManager.getDeviceConnFactoryManagers()[1].getConnState();
        if (connState) {
            Log.e("TAG", "zxc1111");
        } else {
            Log.e("TAG", "zxc2222");
        }
        long count = printEntity.getQuantity();
        int max = ZShrPrefencs.getInstance().getPosMax();
        if (max != 0) {
            if (count >= max) {
                count = max;
            }
        }

        ArrayList<byte[]> list = new ArrayList<byte[]>();
        for (int i = 0; i < count; i++) {
            //在打印请可以先设置打印内容的字符编码类型，默认为gbk，请选择打印机可识别的类型，参看编程手册，打印代码页
            DataForSendToPrinterTSC.setCharsetName("gbk");
            //不设置，默认为gbk
            //通过工具类得到一个指令的byte[]数据,以文本为例
            //首先得设置size标签尺寸,宽60mm,高30mm,也可以调用以dot或inch为单位的方法具体换算参考编程手册
            byte[] data = DataForSendToPrinterTSC.sizeBymm(80, 40);
            list.add(data);
            //设置Gap,同上
            list.add(DataForSendToPrinterTSC.gapBymm(2, 0));
            //清除 缓存
            list.add(DataForSendToPrinterTSC.cls());
            list.add(DataForSendToPrinterTSC.text(20, 30, "TSS24.BF2", 0, 1, 1, AppUserToken.getInstance().getResult().getCompany_name()));
            list.add(DataForSendToPrinterTSC.text(430, 30, "TSS24.BF2", 0, 1, 1, "第 " + (i + 1) + " 件"));
//                    String station = printEntity.getStartStation() + printEntity.getArticle_number()
//                            .substring(printEntity.getArticle_number().length() - 6, printEntity.getArticle_number().length());
//                    String startStation = StringUtils.getNullablString(printEntity.getStartStation());
//                    String endStation = StringUtils.getNullablString(printEntity.getEndStation());

            String station = "";

            if (printEntity.getTransit_destination() != null) {
                station = printEntity.getTransit_destination();
            } else {
                station = printEntity.getEndStation();
            }

            String temp = printEntity.getStartStation() + printEntity.getArticle_number()
                    .substring(printEntity.getArticle_number().length() - 6, printEntity.getArticle_number().length()) + station;

            int length = temp.length();
            int x = 0;
            switch (length) {
                case 12:
                    x = 80;
                    break;
                case 11:
                    x = 100;
                    break;
                case 10:
                    x = 130;
                    break;
                default:
                    x = 80;
                    break;
            }

            list.add(DataForSendToPrinterTSC.text(x, 70, "TSS24.BF2", 0, 2, 2, temp));

//                    list.add(DataForSendToPrinterTSC.text(20, 135, "TSS24.BF2", 0, 1, 1, StringUtils.getNullablString(printEntity.getConsignee()) + " 收"));
            list.add(DataForSendToPrinterTSC.text(400, 135, "TSS24.BF2", 0, 1, 1, "货物: " + StringUtils.getNullablString(printEntity.getGoods_name())));

//                    list.add(DataForSendToPrinterTSC.barCode(
//                            135, 165, "128", 90, 1, 0, 2, 2,
//                            printEntity.getBarCode()));

            list.add(DataForSendToPrinterTSC.text(80, 175, "TSS24.BF2", 0, 3, 3, StringUtils.getNullablString(printEntity.getConsignee()) + " 收"));


            list.add(DataForSendToPrinterTSC.text(20, 285, "TSS24.BF2", 0, 1, 1, "www.chinniu.com 监制"));
            list.add(DataForSendToPrinterTSC.text(400, 285, "TSS24.BF2", 0, 1, 1, printEntity.getInvoice_date()));

            //打印
            list.add(DataForSendToPrinterTSC.print(1));
        }

        Vector<Byte> datas = new Vector<>();
        for (int i = 0; i < list.size(); i++) {
            for (int k = 0; k < list.get(i).length; k++) {
                datas.add(list.get(i)[k]);
            }
        }
        DeviceConnFactoryManager.getDeviceConnFactoryManagers()[1].sendDataImmediately(datas);
    }

    public void sendPosBBSimple(final BillingDrawMain printEntity) {
        boolean connState = DeviceConnFactoryManager.getDeviceConnFactoryManagers()[1].getConnState();
        if (connState) {
            Log.e("TAG", "zxc1111");
        } else {
            Log.e("TAG", "zxc2222");
        }
        long count = printEntity.getQuantity();
        int max = ZShrPrefencs.getInstance().getPosMax();
        if (max != 0) {
            if (count >= max) {
                count = max;
            }
        }

        ArrayList<byte[]> list = new ArrayList<byte[]>();
        for (int i = 0; i < count; i++) {
            //在打印请可以先设置打印内容的字符编码类型，默认为gbk，请选择打印机可识别的类型，参看编程手册，打印代码页
            DataForSendToPrinterTSC.setCharsetName("gbk");
            //不设置，默认为gbk
            //通过工具类得到一个指令的byte[]数据,以文本为例
            //首先得设置size标签尺寸,宽60mm,高30mm,也可以调用以dot或inch为单位的方法具体换算参考编程手册
            byte[] data = DataForSendToPrinterTSC.sizeBymm(80, 40);
            list.add(data);
            //设置Gap,同上
            list.add(DataForSendToPrinterTSC.gapBymm(2, 0));
            //清除 缓存
            list.add(DataForSendToPrinterTSC.cls());
            list.add(DataForSendToPrinterTSC.text(20, 30, "TSS24.BF2", 0, 1, 1, AppUserToken.getInstance().getResult().getCompany_name()));

            String station = "";

            if (printEntity.getTransit_destination() != null) {
                station = printEntity.getTransit_destination();
            } else {
                station = printEntity.getEndStation();
            }

            String temp = printEntity.getArticle_number()
                    .substring(printEntity.getArticle_number().length() - 6, printEntity.getArticle_number().length());

            list.add(DataForSendToPrinterTSC.text(220, 30, "TSS24.BF2", 0, 3, 3, station));
            list.add(DataForSendToPrinterTSC.text(470, 30, "TSS24.BF2", 0, 1, 1, "第 " + (i + 1) + " 件"));
            list.add(DataForSendToPrinterTSC.text(180, 140, "TSS24.BF2", 0, 3, 3, temp));
            list.add(DataForSendToPrinterTSC.text(400, 180, "TSS24.BF2", 0, 2, 2, StringUtils.getNullablString(printEntity.getConsignee())));
            list.add(DataForSendToPrinterTSC.text(20, 285, "TSS24.BF2", 0, 1, 1, "www.chinniu.com 监制"));
            list.add(DataForSendToPrinterTSC.text(400, 285, "TSS24.BF2", 0, 1, 1, printEntity.getInvoice_date()));

            //打印
            list.add(DataForSendToPrinterTSC.print(1));
        }

        Vector<Byte> datas = new Vector<>();
        for (int i = 0; i < list.size(); i++) {
            for (int k = 0; k < list.get(i).length; k++) {
                datas.add(list.get(i)[k]);
            }
        }
        DeviceConnFactoryManager.getDeviceConnFactoryManagers()[1].sendDataImmediately(datas);
    }

    public void sendPostBBBigCompany(final BillingDrawMain printEntity) {
        boolean connState = DeviceConnFactoryManager.getDeviceConnFactoryManagers()[1].getConnState();
        if (connState) {
            Log.e("TAG", "zxc1111");
        } else {
            Log.e("TAG", "zxc2222");
        }
        long count = printEntity.getQuantity();
        int max = ZShrPrefencs.getInstance().getPosMax();
        if (max != 0) {
            if (count >= max) {
                count = max;
            }
        }

        ArrayList<byte[]> list = new ArrayList<byte[]>();
        for (int i = 0; i < count; i++) {
            //在打印请可以先设置打印内容的字符编码类型，默认为gbk，请选择打印机可识别的类型，参看编程手册，打印代码页
            DataForSendToPrinterTSC.setCharsetName("gbk");
            //不设置，默认为gbk
            //通过工具类得到一个指令的byte[]数据,以文本为例
            //首先得设置size标签尺寸,宽60mm,高30mm,也可以调用以dot或inch为单位的方法具体换算参考编程手册
            byte[] data = DataForSendToPrinterTSC.sizeBymm(80, 40);
            list.add(data);
            //设置Gap,同上
            list.add(DataForSendToPrinterTSC.gapBymm(2, 0));
            //清除 缓存
            list.add(DataForSendToPrinterTSC.cls());
            list.add(DataForSendToPrinterTSC.text(20, 35, "TSS24.BF2", 0, 2, 2, AppUserToken.getInstance().getResult().getCompany_name()));
            list.add(DataForSendToPrinterTSC.text(430, 30, "TSS24.BF2", 0, 1, 1, "第 " + (i + 1) + " 件"));

            String station = "";

            if (printEntity.getTransit_destination() != null) {
                station = printEntity.getTransit_destination();
            } else {
                station = printEntity.getEndStation();
            }

            String temp = printEntity.getStartStation() + printEntity.getArticle_number()
                    .substring(printEntity.getArticle_number().length() - 6, printEntity.getArticle_number().length()) + station;

            int length = temp.length();
            int x = 0;
            switch (length) {
                case 12:
                    x = 80;
                    break;
                case 11:
                    x = 100;
                    break;
                case 10:
                    x = 130;
                    break;
                default:
                    x = 80;
                    break;
            }

            list.add(DataForSendToPrinterTSC.text(x, 90, "TSS24.BF2", 0, 2, 2, temp));

            list.add(DataForSendToPrinterTSC.text(80, 175, "TSS24.BF2", 0, 3, 3, StringUtils.getNullablString(printEntity.getConsignee()) + " 收"));


            list.add(DataForSendToPrinterTSC.text(20, 285, "TSS24.BF2", 0, 1, 1, "www.chinniu.com 监制"));
            list.add(DataForSendToPrinterTSC.text(400, 285, "TSS24.BF2", 0, 1, 1, printEntity.getInvoice_date()));

            //打印
            list.add(DataForSendToPrinterTSC.print(1));
        }

        Vector<Byte> datas = new Vector<>();
        for (int i = 0; i < list.size(); i++) {
            for (int k = 0; k < list.get(i).length; k++) {
                datas.add(list.get(i)[k]);
            }
        }
        DeviceConnFactoryManager.getDeviceConnFactoryManagers()[1].sendDataImmediately(datas);
    }


    //打印装车
    public void sendLoadRecepitX() {
        int aaaa = billEntity.size() % 10;
        int pages = billEntity.size() / 10;
        if (aaaa != 0) {
            pages = pages + 1;
        }
        EscCommand esc = new EscCommand();
        esc.addInitializePrinter();
        for (int i = 1; i <= pages; i++) {
            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
            esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
            esc.addPrintAndLineFeed();
            esc.addText(AppUserToken.getInstance().getResult().getPoint_name() + "装车清单\n");
            esc.addPrintAndLineFeed();
            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
            esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
            esc.addText("打印时间 " + DateUtil.getCUrrentDateYMDHM() + "   车牌号: " + billEntity.get(0).getPlate_number());
            esc.addPrintAndLineFeed();
            esc.addText("------------------------------------------------");

            if (i != pages) {
                for (int k = (i - 1) * 10; k < ((i - 1) * 10 + 10); k++) {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);

                    esc.addText("货号: " + billEntity.get(k).getArticle_number());
                    esc.addSetHorAndVerMotionUnits((byte) 4, (byte) 0);
                    esc.addSetAbsolutePrintPosition((short) 6);

                    if ((billEntity.get(k).getConsignee() == null
                            || billEntity.get(k).getConsignee().equals(""))
                            && (billEntity.get(k).getConsignee_phone() == null
                            || billEntity.get(k).getConsignee_phone().equals(""))) {

                        esc.addText("未填写收货人信息");
                        esc.addPrintAndLineFeed();

                    } else {
                        esc.addText((billEntity.get(k).getConsignee() == null ? "" : billEntity.get(k).getConsignee()) + " " + (billEntity.get(k).getConsignee_phone() == null ? "" : billEntity.get(k).getConsignee_phone()));
                        esc.addPrintAndLineFeed();

                    }
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    esc.addText((billEntity.get(k).getStartStation()
                            + "----" + billEntity.get(k).getEndStation()) + "   " + "开票日期: " + billEntity.get(k).getInvoice_date());
                    esc.addPrintAndLineFeed();

                    String builder = "提付: " + StringUtils.getZeroableStringForDouble(billEntity.get(k).getCollect_payment()) +
                            "   现付: " + StringUtils.getZeroableStringForDouble(billEntity.get(k).getCash_payment()) +
                            "   回付: " + StringUtils.getZeroableStringForDouble(billEntity.get(k).getBack_payment()) +
                            "   月结: " + StringUtils.getZeroableStringForDouble(billEntity.get(k).getMonthly_payment());

                    esc.addText(builder);
                    esc.addPrintAndLineFeed();
                    esc.addText("货物: " + (billEntity.get(k).getGoods_name() == null ? "无" : billEntity.get(k).getGoods_name())
                            + "(" + (billEntity.get(k).getPackaging() == null ? "无" : billEntity.get(k).getPackaging()) + ", "
                            + billEntity.get(k).getDelivery_method() + ")" + " 代收: "
                            + StringUtils.getZeroableStringForDouble(billEntity.get(k).getCollection_fee())
                            + " 回扣: " + StringUtils.getZeroableStringForDouble(billEntity.get(k).getReturn_fee()));

                    esc.addPrintAndLineFeed();
                    esc.addText("------------------------------------------------");

                }

            } else {
                for (int k = (i - 1) * 10; k < billEntity.size(); k++) {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                    esc.addText("货号: " + billEntity.get(k).getArticle_number());

                    esc.addSetHorAndVerMotionUnits((byte) 4, (byte) 0);
                    esc.addSetAbsolutePrintPosition((short) 6);
                    if ((billEntity.get(k).getConsignee() == null
                            || billEntity.get(k).getConsignee().equals(""))
                            && (billEntity.get(k).getConsignee_phone() == null
                            || billEntity.get(k).getConsignee_phone().equals(""))) {

                        esc.addText("未填写收货人信息");
                        esc.addPrintAndLineFeed();

                    } else {
                        esc.addText((billEntity.get(k).getConsignee() == null ? "" : billEntity.get(k).getConsignee()) + " " + (billEntity.get(k).getConsignee_phone() == null ? "" : billEntity.get(k).getConsignee_phone()));
                        esc.addPrintAndLineFeed();
                    }
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    esc.addText((billEntity.get(k).getStartStation()
                            + "----" + billEntity.get(k).getEndStation()) + "   " + "开票日期: " + billEntity.get(k).getInvoice_date());
                    esc.addPrintAndLineFeed();


                    String builder = "提付: " + StringUtils.getZeroableStringForDouble(billEntity.get(k).getCollect_payment()) +
                            "   现付: " + StringUtils.getZeroableStringForDouble(billEntity.get(k).getCash_payment()) +
                            "   回付: " + StringUtils.getZeroableStringForDouble(billEntity.get(k).getBack_payment()) +
                            "   月结: " + StringUtils.getZeroableStringForDouble(billEntity.get(k).getMonthly_payment());

                    esc.addText(builder);
                    esc.addPrintAndLineFeed();
                    esc.addText("货物: " + (billEntity.get(k).getGoods_name() == null ? "无" : billEntity.get(k).getGoods_name())
                            + "(" + (billEntity.get(k).getPackaging() == null ? "无" : billEntity.get(k).getPackaging()) + ", "
                            + billEntity.get(k).getDelivery_method() + ")" + " 代收: "
                            + StringUtils.getZeroableStringForDouble(billEntity.get(k).getCollection_fee())
                            + " 回扣: " + StringUtils.getZeroableStringForDouble(billEntity.get(k).getReturn_fee()));

                    esc.addPrintAndLineFeed();
                    esc.addText("------------------------------------------------");

                    if (k == billEntity.size() - 1) {
                        esc.addPrintAndLineFeed();
                        //运费 票数 总计 声明价值
                        double quantity = 0;
                        double freight = 0;
                        double advance = 0;
                        double valuation = 0;
                        double delivery = 0;
                        double receving = 0;
                        double upstair = 0;
                        double packing = 0;
                        double forklift = 0;
                        double handling = 0;

                        double collec_fee = 0;
                        double totalall = 0;
                        double cashPayment = 0;
                        double collectPayment = 0;
                        double back_payment = 0;
                        double monthly = 0;
                        double deduction = 0;
                        double re_fee = 0;

                        for (BillingDrawMain entity : billEntity) {
                            quantity += getNotNullDouble(Double.valueOf(entity.getQuantity()));
                            freight += getNotNullDouble(entity.getFreight());
                            advance += getNotNullDouble(entity.getAdvance());
                            valuation += getNotNullDouble(entity.getValuation_fee());
                            delivery += getNotNullDouble(entity.getDelivery_fee());
                            receving += getNotNullDouble(entity.getReceiving_fee());
                            upstair += getNotNullDouble(entity.getUpstair_fee());
                            packing += getNotNullDouble(entity.getPacking_fee());
                            forklift += getNotNullDouble(entity.getForklift_fee());
                            handling += getNotNullDouble(entity.getHandling_fee());
                            cashPayment += getNotNullDouble(entity.getCash_payment());
                            collectPayment += getNotNullDouble(entity.getCollect_payment());
                            collec_fee += getNotNullDouble(entity.getCollection_fee());
                            totalall += getNotNullDouble(entity.getTotal_freight());
                            back_payment += getNotNullDouble(entity.getBack_payment());
                            monthly += getNotNullDouble(entity.getMonthly_payment());
                            deduction += getNotNullDouble(entity.getPayment_deduction());
                            re_fee += getNotNullDouble(entity.getReturn_fee());
                        }

                        esc.addText("总计");
                        esc.addPrintAndLineFeed();
                        esc.addText("总件数: " + StringUtils.getZeroableStringForDouble(quantity));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 7);
                        esc.addText("运费: " + StringUtils.getZeroableStringForDouble(freight));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 14);
                        esc.addText("垫付款: " + StringUtils.getZeroableStringForDouble(advance));
                        esc.addPrintAndLineFeed();

                        esc.addText("保价费: " + StringUtils.getZeroableStringForDouble(valuation));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 7);
                        esc.addText("送货费: " + StringUtils.getZeroableStringForDouble(delivery));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 14);
                        esc.addText("接货费: " + StringUtils.getZeroableStringForDouble(receving));
                        esc.addPrintAndLineFeed();

                        esc.addText("上楼费: " + StringUtils.getZeroableStringForDouble(upstair));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 7);
                        esc.addText("包装费: " + StringUtils.getZeroableStringForDouble(packing));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 14);
                        esc.addText("叉吊费: " + StringUtils.getZeroableStringForDouble(forklift));
                        esc.addPrintAndLineFeed();

                        esc.addText("装卸费: " + StringUtils.getZeroableStringForDouble(handling));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 7);
                        esc.addText("运费合计: " + StringUtils.getZeroableStringForDouble(totalall));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 14);
                        esc.addText("代收: " + StringUtils.getZeroableStringForDouble(collec_fee));
                        esc.addPrintAndLineFeed();

                        esc.addText("现付: " + StringUtils.getZeroableStringForDouble(cashPayment));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 7);
                        esc.addText("提付: " + StringUtils.getZeroableStringForDouble(collectPayment));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 14);
                        esc.addText("回付: " + StringUtils.getZeroableStringForDouble(back_payment));
                        esc.addPrintAndLineFeed();

                        esc.addText("月结: " + StringUtils.getZeroableStringForDouble(monthly));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 7);
                        esc.addText("货款扣: " + StringUtils.getZeroableStringForDouble(deduction));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 14);
                        esc.addText("回扣: " + StringUtils.getZeroableStringForDouble(re_fee));
                        esc.addPrintAndLineFeed();

                        esc.addText("------------------------------------------------");

                        esc.addText("承运司机(签字): ");
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 12);
                        esc.addText("交接人(签字): ");
                        esc.addPrintAndLineFeed();

                        esc.addText("发车网点: " + AppUserToken.getInstance().getResult().getCompany_name());

                        esc.addPrintAndLineFeed();

                        esc.addText("制单: " + (AppUserToken.getInstance().getResult().getUser_name() == null ? "" : AppUserToken.getInstance().getResult().getUser_name()));
//                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
//                        esc.addSetAbsolutePrintPosition((short) 7);
//                        esc.addText("打印时间 " + DateUtil.getCUrrentDateYMDHM());
                        esc.addPrintAndLineFeed();

                        esc.addText("------------------------------------------------");
                        esc.addPrintAndLineFeed();
                        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
                        esc.addText("骏诚勤牛供应链监制   www.chinniu.com");
                        esc.addPrintAndLineFeed();

                        esc.addPrintAndLineFeed();
                        esc.addPrintAndLineFeed();
//                        esc.addCutAndFeedPaper((byte) 3);
                    }
                }
            }
            //总计 票 件 运费
            esc.addPrintAndLineFeed();
            esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
            esc.addText("----第" + i + "页  共" + (pages) + "页----");
            esc.addPrintAndLineFeed();
            esc.addCutAndFeedPaper((byte) 3);
        }

        if (ZShrPrefencs.getInstance().getNetOn() == 0) {
            Vector<Byte> datas = esc.getCommand();//发送数据
            DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].sendDataImmediately(datas);
        } else {
            final List<byte[]> list = new ArrayList<>();
            Vector<Byte> datas = esc.getCommand();//发送数据
            byte[] bytes = GpUtils.ByteTo_byte(datas);
            list.add(bytes);

            MainActivity.Companion.getBinder().writeDataByYouself(
                    new UiExecute() {
                        @Override
                        public void onsucess() {
                            MainActivity.Companion.getBinder().disconnectCurrentPort(new UiExecute() {
                                @Override
                                public void onsucess() {
                                    Log.e("TAG", "打印完毕，断开成功");
                                }

                                @Override
                                public void onfailed() {
                                    Log.e("TAG", "打印完毕，断开失败");
                                }
                            });

                        }

                        @Override
                        public void onfailed() {

                        }
                    }, new ProcessData() {
                        @Override
                        public List<byte[]> processDataBeforeSend() {
                            list.add(DataForSendToPrinterPos80.initializePrinter());
                            return list;
                        }
                    });
        }
    }

    private Double getNotNullDouble(Double sth) {
        return sth != null ? sth : 0.0;
    }


    public void batchPrint(List<BillingDrawMain> entities) {
        short temp = 12;
        if (ZShrPrefencs.getInstance().getNetOn() == 0) {
            final EscCommand esc = new EscCommand();
            esc.addInitializePrinter();
            for (int i = 0; i < entities.size(); i++) {
                BillingDrawMain printEntity = entities.get(i);
                String fuckOff = printEntity.getTotal_freight_receipts();
                String otherFee = "";
                String mutiFee = "";
                if (otherFee != null) {
                    try {
                        String[] shit = fuckOff.split("&&");
                        otherFee = shit[0];
                        mutiFee = shit[1];
                    } catch (Exception e) {
                        Log.e("TAG", "shit is happened");
                    }
                }

                if (ZShrPrefencs.getInstance().getBigNumber() == 1) {
                    esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                    String aa = StringUtils.getNullablString(printEntity.getArticle_number());
                    if (aa != null) {
                        esc.addText(aa.substring(4, aa.length()));
                        esc.addPrintAndLineFeed();
                    }
                }

                esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                if (!isSign) {
                    esc.addText(String.format("%s货运单", AppUserToken.getInstance().getResult().getCompany_name()));
                } else {
                    esc.addText(String.format("%s签收单", AppUserToken.getInstance().getResult().getCompany_name()));
                }

                esc.addPrintAndLineFeed();
                Date date = null;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA).parse(printEntity.getInvoice_date());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String aa = date.getHours() + "" + (String.valueOf(date.getMinutes()).length() == 1 ? "0" + String.valueOf(date.getMinutes()) : String.valueOf(date.getMinutes()));

                int bb = Integer.parseInt(aa);
                int cc = ZShrPrefencs.getInstance().getTimeDistr();
                String ban = "";
                if (cc != 0) {
                    if (bb <= cc) {
                        ban = "(中班)";
                    } else {
                        ban = "(晚班)";
                    }
                }
                if (printEntity.getTransit_destination() != null) {
                    esc.addText(printEntity.getStartStation() + "—" + StringUtils.getNullablString(printEntity.getTransit_destination()) + ban + "\n");
                } else {
                    esc.addText(printEntity.getStartStation() + "—" + StringUtils.getNullablString(printEntity.getEndStation()) + ban + "\n");
                }
                esc.addPrintAndLineFeed();

                if (ZShrPrefencs.getInstance().getHintParams() != 0 && i == 0) {
                    esc.addText("*凭此联兑代收*");
                    esc.addPrintAndLineFeed();
                    esc.addPrintAndLineFeed();
                }

                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                if (printEntity.getInvoice_date() != null) {
                    if (buda == 9999) {
                        esc.addText(String.format("开票日期: %s", StringUtils.getNullablString(printEntity.getInvoice_date())) + "(补)");
                    } else {
                        esc.addText(String.format("开票日期: %s", StringUtils.getNullablString(printEntity.getInvoice_date())));
                    }
                } else {
                    esc.addText("开票日期: ");
                }
                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                esc.addSetAbsolutePrintPosition((short) 14);
                esc.addText(String.format("开票: %s", StringUtils.getNullablString(printEntity.getUser_name())));
                esc.addPrintAndLineFeed();
//
//            esc.addText(String.format("运单号: %s", StringUtils.getNullablString(printEntity.getWaybill_number())));
//            esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
//            esc.addSetAbsolutePrintPosition((short) 14);
//
//            esc.addPrintAndLineFeed();

//            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
//            esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
//            if (printEntity.getInput_article_number() != null) {
//                esc.addText("货号: " + StringUtils.getNullablString(printEntity.getArticle_number()
//                        + "(" + printEntity.getInput_article_number() + ")"));
//            } else {
//                esc.addText("货号: " + StringUtils.getNullablString(printEntity.getArticle_number()));
//            }

                esc.addText("货号: " + StringUtils.getNullablString(printEntity.getArticle_number()) + " 自录货号: " + StringUtils.getNullablString(printEntity.getInput_article_number()));

                esc.addPrintAndLineFeed();
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);

                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();


                String getterName = printEntity.getConsignee();
                String getterPhone = printEntity.getConsignee_phone();

                int reeee = ZShrPrefencs.getInstance().getReciveSize();
                if (getterName == null || getterName.equals("")) {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    esc.addText("未填写收货人姓名");
                } else {
                    if (reeee == 0) {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                    } else {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    }
                    esc.addText(String.format("收货人: %s", getterName));
                }
                if (reeee == 0) {
                    esc.addPrintAndLineFeed();
                } else {
                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                    esc.addSetAbsolutePrintPosition((short) 12);
                }
                if (getterPhone == null || getterPhone.equals("")) {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    esc.addText("未填写收货人电话");
                } else {
                    if (reeee == 0) {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                    } else {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    }
                    esc.addText(String.format("电话: %s", getterPhone));
                }


                esc.addPrintAndLineFeed();
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();

                String senderName = printEntity.getConsigner();
                String senderPhone = printEntity.getConsigner_phone();

                int sendddd = ZShrPrefencs.getInstance().getSendSize();
                if (senderName == null || senderName.equals("")) {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    esc.addText("未填写发货人姓名");
                } else {
                    if (sendddd == 0) {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                    } else {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    }
                    esc.addText(String.format("发货人: %s", senderName));
                }

                if (sendddd == 0) {
                    esc.addPrintAndLineFeed();
                } else {
                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                    esc.addSetAbsolutePrintPosition((short) 12);
                }
                if (senderPhone == null || senderPhone.equals("")) {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    esc.addText("未填写发货人电话");
                } else {
                    if (sendddd == 0) {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                    } else {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    }
                    esc.addText(String.format("电话: %s", senderPhone));
                }


//                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
//                esc.addText(String.format("发货人: %s", StringUtils.getNullablString(printEntity.getConsigner())));
//                esc.addPrintAndLineFeed();
//                esc.addText(String.format("电话: %s", StringUtils.getNullablString(printEntity.getConsigner_phone())));
                esc.addPrintAndLineFeed();
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();

                int numberrrr = ZShrPrefencs.getInstance().getNumberSize();

                if (numberrrr == 0) {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                } else {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                }
                esc.addText("数量: " + printEntity.getQuantity() + "件");
                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                esc.addSetAbsolutePrintPosition((short) 12);
                esc.addText("[" + printEntity.getDelivery_method() + "]");


                esc.addPrintAndLineFeed();
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);

                esc.addText(String.format("货物名称: %s", StringUtils.getNullablString(printEntity.getGoods_name())));
                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                esc.addSetAbsolutePrintPosition((short) temp);
                esc.addText(String.format("包装方式: %s", StringUtils.getNullablString(printEntity.getPackaging())));
                esc.addPrintAndLineFeed();

                esc.addText("------------------------------------------------");

                String shitON = "";
                if (ZShrPrefencs.getInstance().getPriceDetail() == 1) {
                    shitON = "运费合计: ";
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    esc.addPrintAndLineFeed();
                    if (printEntity.getFreight() == null || printEntity.getFreight() == 0.0) {
                        esc.addText("运费: 0元");
                    } else {
                        esc.addText(String.format("运费: %s元", ZDoubleFormat.zFormat(printEntity.getFreight().toString())));
                    }
                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                    esc.addSetAbsolutePrintPosition((short) temp);

                    if (printEntity.getAdvance() == null || printEntity.getAdvance() == 0.0) {
                        esc.addText("垫付款: " + "0元");
                    } else {
                        esc.addText(String.format("垫付款: %s元", ZDoubleFormat.zFormat(printEntity.getAdvance().toString())));
                    }
                    esc.addPrintAndLineFeed();

                    if (printEntity.getDelivery_fee() == null || printEntity.getDelivery_fee() == 0.0) {
                        esc.addText("送货费: " + "0元");
                    } else {
                        esc.addText(String.format("送货费: %s元", ZDoubleFormat.zFormat(printEntity.getDelivery_fee().toString())));
                    }
                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                    esc.addSetAbsolutePrintPosition((short) temp);
                    if (printEntity.getValuation_fee() == null || printEntity.getValuation_fee() == 0.0) {
                        esc.addText("保价费: " + "0元");
                    } else {
                        esc.addText(String.format("保价费: %s元", ZDoubleFormat.zFormat(printEntity.getValuation_fee().toString())));
                    }
                    esc.addPrintAndLineFeed();

                    esc.addText("其它费用: " + otherFee);
//                    esc.addPrintAndLineFeed();
                } else {
                    shitON = "运费: ";
                }

                if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                } else {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                }

                if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                    esc.addSetAbsolutePrintPosition((short) temp);
                } else {
                    esc.addPrintAndLineFeed();
                }

                if (printEntity.getCollection_fee() == null || printEntity.getCollection_fee().equals("")) {
                    if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                        esc.addText("代收: 0元");
                    } else {
                        esc.addText("代收: ");
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 6);
                        esc.addText("0元");
                        if (ZShrPrefencs.getInstance().getBigDianFu() == 1) {
                            esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                            esc.addSetAbsolutePrintPosition((short) 12);
                            esc.addText("垫付: " + ((printEntity.getAdvance() == null || printEntity.getAdvance() == 0.0) ? "0元" : (ZDoubleFormat.zFormat(String.valueOf(printEntity.getAdvance())) + "元")));
                        }
                    }
                } else {
                    if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                        esc.addText("代收: " + ZDoubleFormat.zFormat(printEntity.getCollection_fee().toString()) + "元");
                    } else {
                        esc.addText("代收: ");
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 6);
                        esc.addText(ZDoubleFormat.zFormat(printEntity.getCollection_fee().toString()) + "元");
                        if (ZShrPrefencs.getInstance().getBigDianFu() == 1) {
                            esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                            esc.addSetAbsolutePrintPosition((short) 12);
                            esc.addText("垫付: " + ((printEntity.getAdvance() == null || printEntity.getAdvance() == 0.0) ? "0元" : (ZDoubleFormat.zFormat(String.valueOf(printEntity.getAdvance())) + "元")));
                        }
                    }
                }
                esc.addPrintAndLineFeed();

                if (printEntity.getTotal_freight() == null || printEntity.getTotal_freight() == 0.0) {
                    if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                        if (printEntity.getPayment_method() != null && printEntity.getPayment_method().equals("多笔付")) {
                            esc.addText(shitON + "0元[" + mutiFee + "]");
                        } else {
                            esc.addText(shitON + "0元[" + printEntity.getPayment_method() + "]");
                        }
                    } else {
                        esc.addText(shitON);
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 6);
                        esc.addText("0元");
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 12);
                        if (printEntity.getPayment_method() != null && printEntity.getPayment_method().equals("多笔付")) {
                            esc.addText("[" + mutiFee + "]");
                        } else {
                            esc.addText("[" + printEntity.getPayment_method() + "]");
                        }
                    }
                } else {
                    if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                        if (printEntity.getPayment_method() != null && printEntity.getPayment_method().equals("多笔付")) {
                            esc.addText(String.format(shitON + "%s元[" + mutiFee + "]", ZDoubleFormat.zFormat(printEntity.getTotal_freight().toString())));
                        } else {
                            esc.addText(String.format(shitON + "%s元[" + printEntity.getPayment_method() + "]", ZDoubleFormat.zFormat(printEntity.getTotal_freight().toString())));
                        }
                    } else {
                        esc.addText(String.format(shitON));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 6);
                        double advance = (printEntity.getAdvance() == null ? 0.0 : printEntity.getAdvance());

                        Double total_freight = printEntity.getTotal_freight();
                        if (total_freight == null) {
                            total_freight = 0.0;
                        }
                        if (ZShrPrefencs.getInstance().getBigDianFu() == 1) {
                            total_freight = total_freight - advance;
                        }
                        esc.addText(String.format("%s元", ZDoubleFormat.zFormat(total_freight.toString())));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 12);
                        if (printEntity.getPayment_method() != null && printEntity.getPayment_method().equals("多笔付")) {
                            esc.addText("[" + mutiFee + "]");
                        } else {
                            esc.addText("[" + printEntity.getPayment_method() + "]");
                        }
                    }
                }
//                if (isSign) {
//                    Double totalFee = StringUtils.getDoubleNotNull(printEntity.getFreight())
//                            + StringUtils.getDoubleNotNull(printEntity.getReceiving_fee())
//                            + StringUtils.getDoubleNotNull(printEntity.getHandling_fee())
//                            + StringUtils.getDoubleNotNull(printEntity.getForklift_fee())
//                            + StringUtils.getDoubleNotNull(printEntity.getPacking_fee())
//                            + StringUtils.getDoubleNotNull(printEntity.getUpstair_fee())
//                            + StringUtils.getDoubleNotNull(printEntity.getAdvance())
//                            + StringUtils.getDoubleNotNull(printEntity.getDelivery_fee())
//                            + StringUtils.getDoubleNotNull(printEntity.getValuation_fee())
//                            + StringUtils.getDoubleNotNull(printEntity.getCollection_fee())
//                            - StringUtils.getDoubleNotNull(printEntity.getCash_payment());
//                    esc.addText("应收费用: " + ZDoubleFormat.zFormat(totalFee.toString()) + "元");
//                    esc.addPrintAndLineFeed();
//                }

                esc.addPrintAndLineFeed();
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();

                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);

                Double total = 0.0;
                Double daishou = 0.0;
                double allMoney = 0.0;
                if (printEntity.getTotal_freight() != null) {
                    total = printEntity.getTotal_freight();
                }
                if (printEntity.getCollection_fee() != null) {
                    daishou = printEntity.getCollection_fee();
                }
                if (printEntity.getPayment_method() != null) {
                    if (printEntity.getPayment_method().equals("到付")) {
                        allMoney = total + daishou;
                    } else if (printEntity.getPayment_method().equals("现付")) {
                        allMoney = daishou;
                    } else if (printEntity.getPayment_method().equals("多笔付")) {
                        Double collect_payment = printEntity.getCollect_payment();
                        int tempx = 0;
                        if (collect_payment == null) {
                            tempx = 0;
                        } else {
                            tempx = Integer.parseInt(ZDoubleFormat.zFormat(String.valueOf(collect_payment)));
                        }
                        allMoney = tempx + daishou;
                    }
                }

                if (!isSign) {
                    esc.addText("     合计: " + ZDoubleFormat.zFormat(allMoney + "") + "元");
                } else {
                    esc.addText("     应收: " + ZDoubleFormat.zFormat(allMoney + "") + "元");
                }

                esc.addPrintAndLineFeed();
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();

                if (printEntity.getBarCode() != null) {
                    if (ZShrPrefencs.getInstance().getIsBarCode() == 1) {
                        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
                        esc.addSelectPrintingPositionForHRICharacters(EscCommand.HRI_POSITION.BELOW);
                        // 设置条码可识别字符位置在条码下方
                        // 设置条码高度为60点
                        esc.addSetBarcodeHeight((byte) 80);
                        // 设置条码单元宽度为1
                        esc.addSetBarcodeWidth((byte) 2);
                        // 打印Code128码
                        String barCode = printEntity.getBarCode();
                        esc.addCODE128(esc.genCode128(StringUtils.getNullablString(barCode)));
                        esc.addPrintAndLineFeed();
                    }
                }

                if (printEntity.getRemarks() != null && !printEntity.getRemarks().equals("")) {
                    esc.addText("备注: " + printEntity.getRemarks());
                    esc.addPrintAndLineFeed();
                }

                if (isSign) {
                    esc.addText("签收人: ");
                    esc.addPrintAndLineFeed();
                    esc.addPrintAndLineFeed();
                }

                int space = ZShrPrefencs.getInstance().getSpaceCount();

                for (int p = 0; p < space; p++) {
                    esc.addPrintAndLineFeed();
                }

                String notice = printEntity.getLogistics_notice();
                if (!isSign) {
                    if (notice != null && !notice.isEmpty()) {
                        esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                        esc.addText("声明");
                        esc.addPrintAndLineFeed();
                        esc.addText(notice);
                    }
                }
                if (AppUserToken.getInstance().getResult().getLogistics_uuid() == 2306) {
                    esc.addPrintAndLineFeed();
                    esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                    esc.addText("单子有效期3个月");
                }
//            List<Declaration> notices = AppUserToken.getInstance().getDeclars();
//            if (notices != null && notices.size() != 0) {
//                for (int k = 0; k < notices.size(); k++) {
//                    esc.addText((k + 1) + "," + notices.get(k).getLogistics_notice());
//                    esc.addPrintAndLineFeed();
//                }
//            } else {
//                esc.addText("1,货主通过平台直收货款;承运公司确保传统代收货款安全。");
//                esc.addPrintAndLineFeed();
//                esc.addText("2,保价费按实际货物价值缴纳(特殊货品除外)。如未保价，按最高不超过运费10倍赔偿，易碎品货损的6%属正常损耗。");
//                esc.addPrintAndLineFeed();
//                esc.addText("3,危险品、禁运品不予运输。虚报货名，后果自负。");
//                esc.addPrintAndLineFeed();
//            }
                esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                esc.addPrintAndLineFeed();
                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();

                String pointName = AppUserToken.getInstance().getResult().getPoint_name();
                String pointPhone = printEntity.getStartPoint_phone();

                String endName = printEntity.getEndStation();
                String endPoint = printEntity.getPoint_owner_phone_o();


                esc.addText("开票网点: " + StringUtils.getNullablString(pointName));
                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                esc.addSetAbsolutePrintPosition((short) 10);

                if (pointPhone != null) {
                    String tempaaa = pointPhone.split(",")[0];
                    esc.addText("查货电话: " + tempaaa);
                }
                esc.addPrintAndLineFeed();
                if (pointPhone != null) {
                    if (pointPhone.split(",").length == 2) {
                        esc.addSelectJustification(EscCommand.JUSTIFICATION.RIGHT);
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                        esc.addText(pointPhone.split(",")[1]);
                        esc.addPrintAndLineFeed();
                    }
                }
                esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();
                esc.addText("提货网点: " + StringUtils.getNullablString(endName));
                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                esc.addSetAbsolutePrintPosition((short) 10);

                if (endPoint != null) {
                    String tempxx = endPoint.split(",")[0];
                    esc.addText("提货电话: " + tempxx);
                    esc.addPrintAndLineFeed();
                }

                if (endPoint != null) {
                    if (endPoint.split(",").length == 2) {
                        esc.addSelectJustification(EscCommand.JUSTIFICATION.RIGHT);
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                        esc.addText(pointPhone.split(",")[1]);
                        esc.addPrintAndLineFeed();
                    }
                }
                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();

                esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
                esc.addText(" 骏诚勤牛供应链监制" + "  www.chinniu.com");
                esc.addPrintAndLineFeed();
//                esc.addGeneratePlus(LabelCommand.FOOT.F2, (byte) 255, (byte) 255);
                esc.addPrintAndFeedLines((byte) 3);
                esc.addPrintAndFeedLines((byte) 1);
                esc.addCutPaper();

            }
            //for 循环结束
            esc.addCutPaper();
            esc.addQueryPrinterStatus();
            Vector<Byte> datas = esc.getCommand();//发送数据
            DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].sendDataImmediately(datas);
        }
    }
}