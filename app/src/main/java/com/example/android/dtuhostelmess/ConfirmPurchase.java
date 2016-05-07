package com.example.android.dtuhostelmess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import utils.GlobalVariables;
import utils.MyAsyncTask;
import utils.URLS;

public class ConfirmPurchase extends Activity {

    Bundle b;
    Button confirm;
    TextView cbItem, cbPrice, mess, item1, price1, item2, price2, item3, price3, item4, price4, item5, price5, item6, price6, item7, price7;
    String others, otherName, otherPrice, messSelected, confirmItem1, confirmPrice1, confirmItem2, confirmPrice2, confirmItem3, confirmPrice3, confirmItem4, confirmPrice4, confirmItem5, confirmPrice5, confirmItem6, confirmPrice6, confirmItem7, confirmPrice7, confirmId1, confirmId2, confirmId3, confirmId4, confirmId5, confirmId6, confirmId7;
    Spinner dropdown1, dropdown2, dropdown3, dropdown4, dropdown5, dropdown6, dropdown7, dropdownOthers;
    int numberOfItems = 0, onStartCount = 0;
    ProgressBar progressBar;
    LinearLayout L1, L2, L3, L4, L5, L6, L7, Lothers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_purchase);

        onStartCount = 1;
        if (savedInstanceState == null) // 1st time
        {
            this.overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);
        } else // already created so reverse animation
        {
            onStartCount = 2;
        }

        L1 = (LinearLayout) findViewById(R.id.Layout1);
        L2 = (LinearLayout) findViewById(R.id.Layout2);
        L3 = (LinearLayout) findViewById(R.id.Layout3);
        L4 = (LinearLayout) findViewById(R.id.Layout4);
        L5 = (LinearLayout) findViewById(R.id.Layout5);
        L6 = (LinearLayout) findViewById(R.id.Layout6);
        L7 = (LinearLayout) findViewById(R.id.Layout7);

        Lothers = (LinearLayout) findViewById(R.id.linearOthers);
        cbItem = (TextView) findViewById(R.id.itemOthers);
        cbPrice = (TextView) findViewById(R.id.priceOthers);

        dropdown1 = (Spinner) findViewById(R.id.spinner1);
        dropdown2 = (Spinner) findViewById(R.id.spinner2);
        dropdown3 = (Spinner) findViewById(R.id.spinner3);
        dropdown4 = (Spinner) findViewById(R.id.spinner4);
        dropdown5 = (Spinner) findViewById(R.id.spinner5);
        dropdown6 = (Spinner) findViewById(R.id.spinner6);
        dropdown7 = (Spinner) findViewById(R.id.spinner7);
        dropdownOthers = (Spinner) findViewById(R.id.spinnerOthers);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        String[] items = new String[]{"1", "2", "3", "4", "5", "0"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown1.setAdapter(adapter);
        dropdown2.setAdapter(adapter);
        dropdown3.setAdapter(adapter);
        dropdown4.setAdapter(adapter);
        dropdown5.setAdapter(adapter);
        dropdown6.setAdapter(adapter);
        dropdown7.setAdapter(adapter);
        dropdownOthers.setAdapter(adapter);

        dropdown1.setVisibility(View.INVISIBLE);
        dropdown2.setVisibility(View.INVISIBLE);
        dropdown3.setVisibility(View.INVISIBLE);
        dropdown4.setVisibility(View.INVISIBLE);
        dropdown5.setVisibility(View.INVISIBLE);
        dropdown6.setVisibility(View.INVISIBLE);
        dropdown7.setVisibility(View.INVISIBLE);
        dropdownOthers.setVisibility(View.GONE);

        mess = (TextView) findViewById(R.id.tvmess);
        item1 = (TextView) findViewById(R.id.item1);
        price1 = (TextView) findViewById(R.id.price1);
        item2 = (TextView) findViewById(R.id.item2);
        price2 = (TextView) findViewById(R.id.price2);
        item3 = (TextView) findViewById(R.id.item3);
        price3 = (TextView) findViewById(R.id.price3);
        item4 = (TextView) findViewById(R.id.item4);
        price4 = (TextView) findViewById(R.id.price4);
        item5 = (TextView) findViewById(R.id.item5);
        price5 = (TextView) findViewById(R.id.price5);
        item6 = (TextView) findViewById(R.id.item6);
        price6 = (TextView) findViewById(R.id.price6);
        item7 = (TextView) findViewById(R.id.item7);
        price7 = (TextView) findViewById(R.id.price7);

        confirm = (Button) findViewById(R.id.btConfirmOrder);

        b = getIntent().getExtras();
        messSelected = b.getString("mess");
        mess.setText(messSelected);
        numberOfItems = Integer.parseInt(b.getString("TotalItem"));

        if (numberOfItems >= 1) {
            confirmItem1 = b.getString("1");
            confirmPrice1 = b.getString("2").substring(3);
            confirmId1 = b.getString("3");
            L1.setVisibility(View.VISIBLE);
        }

        if (numberOfItems >= 2) {
            confirmItem2 = b.getString("4");
            confirmPrice2 = b.getString("5").substring(3);
            confirmId2 = b.getString("6");
            L2.setVisibility(View.VISIBLE);
        }

        if (numberOfItems >= 3) {
            confirmItem3 = b.getString("7");
            confirmPrice3 = b.getString("8").substring(3);
            confirmId3 = b.getString("9");
            L3.setVisibility(View.VISIBLE);
        }

        if (numberOfItems >= 4) {
            confirmItem4 = b.getString("10");
            confirmPrice4 = b.getString("11").substring(3);
            confirmId4 = b.getString("12");
            L4.setVisibility(View.VISIBLE);
        }

        if (numberOfItems >= 5) {
            confirmItem5 = b.getString("13");
            confirmPrice5 = b.getString("14").substring(3);
            confirmId5 = b.getString("15");
            L5.setVisibility(View.VISIBLE);
        }

        if (numberOfItems >= 6) {
            confirmItem6 = b.getString("16");
            confirmPrice6 = b.getString("17").substring(3);
            confirmId6 = b.getString("18");
            L6.setVisibility(View.VISIBLE);
        }

        if (numberOfItems == 7) {
            confirmItem7 = b.getString("19");
            confirmPrice7 = b.getString("20").substring(3);
            confirmId7 = b.getString("21");
            L7.setVisibility(View.VISIBLE);
        }

        others = b.getString("OthersChecked");

        if (others.equals("1")) {

            Lothers.setVisibility(View.VISIBLE);
            cbItem.setVisibility(View.VISIBLE);
            cbPrice.setVisibility(View.VISIBLE);

            otherName = b.getString("OthersName");
            otherPrice = b.getString("OthersPrice");

            cbItem.setText(otherName);
            GlobalVariables.PriceItemOthers = Integer.parseInt(otherPrice);
            dropdownOthers.setVisibility(View.VISIBLE);
            dropdownOthers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    String quantity = dropdownOthers.getSelectedItem().toString();
                    GlobalVariables.totalItemOthers = Integer.parseInt(quantity) * GlobalVariables.PriceItemOthers;
                    cbPrice.setText("Rs " + GlobalVariables.totalItemOthers);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });
        }

        if (confirmItem1 != null) {
            item1.setText(confirmItem1);
            GlobalVariables.idItem1 = confirmId1;
            GlobalVariables.PriceItem1 = Integer.parseInt(confirmPrice1);
            dropdown1.setVisibility(View.VISIBLE);
            dropdown1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    String quantity = dropdown1.getSelectedItem().toString();
                    GlobalVariables.totalItem1 = Integer.parseInt(quantity) * GlobalVariables.PriceItem1;
                    price1.setText("Rs " + GlobalVariables.totalItem1);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });
        }

        if (confirmItem2 != null) {
            item2.setText(confirmItem2);
            GlobalVariables.idItem2 = confirmId2;
            GlobalVariables.PriceItem2 = Integer.parseInt(confirmPrice2);
            dropdown2.setVisibility(View.VISIBLE);
            dropdown2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    String quantity = dropdown2.getSelectedItem().toString();
                    GlobalVariables.totalItem2 = Integer.parseInt(quantity) * GlobalVariables.PriceItem2;
                    price2.setText("Rs " + GlobalVariables.totalItem2);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });
        }

        if (confirmItem3 != null) {
            item3.setText(confirmItem3);
            GlobalVariables.idItem3 = confirmId3;
            GlobalVariables.PriceItem3 = Integer.parseInt(confirmPrice3);
            dropdown3.setVisibility(View.VISIBLE);
            dropdown3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    String quantity = dropdown3.getSelectedItem().toString();
                    GlobalVariables.totalItem3 = Integer.parseInt(quantity) * GlobalVariables.PriceItem3;
                    price3.setText("Rs " + GlobalVariables.totalItem3);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });
        }

        if (confirmItem4 != null) {
            item4.setText(confirmItem4);
            GlobalVariables.idItem4 = confirmId4;
            GlobalVariables.PriceItem4 = Integer.parseInt(confirmPrice4);
            dropdown4.setVisibility(View.VISIBLE);
            dropdown4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    String quantity = dropdown4.getSelectedItem().toString();
                    GlobalVariables.totalItem4 = Integer.parseInt(quantity) * GlobalVariables.PriceItem4;
                    price4.setText("Rs " + GlobalVariables.totalItem4);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });
        }

        if (confirmItem5 != null) {
            item5.setText(confirmItem5);
            GlobalVariables.idItem5 = confirmId5;
            GlobalVariables.PriceItem5 = Integer.parseInt(confirmPrice5);
            dropdown5.setVisibility(View.VISIBLE);
            dropdown5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    String quantity = dropdown5.getSelectedItem().toString();
                    GlobalVariables.totalItem5 = Integer.parseInt(quantity) * GlobalVariables.PriceItem5;
                    price5.setText("Rs " + GlobalVariables.totalItem5);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });
        }

        if (confirmItem6 != null) {
            item6.setText(confirmItem6);
            GlobalVariables.idItem6 = confirmId6;
            GlobalVariables.PriceItem6 = Integer.parseInt(confirmPrice6);
            dropdown6.setVisibility(View.VISIBLE);
            dropdown6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    String quantity = dropdown6.getSelectedItem().toString();
                    GlobalVariables.totalItem6 = Integer.parseInt(quantity) * GlobalVariables.PriceItem6;
                    price6.setText("Rs " + GlobalVariables.totalItem6);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });
        }

        if (confirmItem7 != null) {
            item7.setText(confirmItem7);
            GlobalVariables.idItem7 = confirmId7;
            GlobalVariables.PriceItem7 = Integer.parseInt(confirmPrice7);
            dropdown7.setVisibility(View.VISIBLE);
            dropdown7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    String quantity = dropdown7.getSelectedItem().toString();
                    GlobalVariables.totalItem7 = Integer.parseInt(quantity) * GlobalVariables.PriceItem7;
                    price7.setText("Rs " + GlobalVariables.totalItem7);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });
        }

        confirmItem1 = null;
        confirmItem2 = null;
        confirmItem3 = null;
        confirmItem4 = null;
        confirmItem5 = null;
        confirmItem6 = null;
        confirmItem7 = null;
        confirmPrice1 = null;
        confirmPrice2 = null;
        confirmPrice3 = null;
        confirmPrice4 = null;
        confirmPrice5 = null;
        confirmPrice6 = null;
        confirmPrice7 = null;
        confirmId1 = null;
        confirmId2 = null;
        confirmId3 = null;
        confirmId4 = null;
        confirmId5 = null;
        confirmId6 = null;
        confirmId7 = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_confirm_purchase, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void confirmOrder(View v) {
        String counter = null;
        progressBar.setVisibility(View.VISIBLE);

        if (messSelected.equals("CVR Mess")) {
            counter = "1";
        } else if (messSelected.equals("HJB Mess")) {
            counter = "2";
        } else if (messSelected.equals("HJB Mess")) {
            counter = "2";
        } else if (messSelected.equals("VVS Mess")) {
            counter = "3";
        } else if (messSelected.equals("Aryabhatta Mess")) {
            counter = "4";
        } else if (messSelected.equals("SNH Mess")) {
            counter = "5";
        }

        if (item1.getText().toString() != "") {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("roll_number", GlobalVariables.currentRollNo);
                jsonObject.put("item_id", GlobalVariables.idItem1);
                jsonObject.put("quantity", dropdown1.getSelectedItem().toString());
                jsonObject.put("cost", GlobalVariables.PriceItem1 + "");
                jsonObject.put("counter_id", counter);
            } catch (Exception e) {
                Toast.makeText(ConfirmPurchase.this, "" + e, Toast.LENGTH_SHORT).show();
                // System.out.println("Exception in json encoding "+e);
            }
            new MyAsyncTask(ConfirmPurchase.this, jsonObject.toString(), URLS.Purchase_URL, new MyAsyncTask.AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONObject response = new JSONObject(output);
                        String resultedMessage = response.getString("responseType");

                        if (resultedMessage.equals("success")) {
                            Toast.makeText(ConfirmPurchase.this, item1.getText().toString() + " added to Bill", Toast.LENGTH_SHORT).show();
                        } else {
                            //response = response.getJSONObject("payload");
                            //String errorMessage = response.getString("message");
                            Toast.makeText(ConfirmPurchase.this, item1.getText().toString() + " not bought", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        // Toast.makeText(MainActivity.this,""+ e, Toast.LENGTH_LONG).show();
                    }
                }
            }).execute();
        }

        if (item2.getText().toString() != "") {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("roll_number", GlobalVariables.currentRollNo);
                jsonObject.put("item_id", GlobalVariables.idItem2);
                jsonObject.put("quantity", dropdown2.getSelectedItem().toString());
                jsonObject.put("cost", GlobalVariables.PriceItem2 + "");
                jsonObject.put("counter_id", counter);
            } catch (Exception e) {
                Toast.makeText(ConfirmPurchase.this, "" + e, Toast.LENGTH_SHORT).show();
                // System.out.println("Exception in json encoding "+e);
            }
            new MyAsyncTask(ConfirmPurchase.this, jsonObject.toString(), URLS.Purchase_URL, new MyAsyncTask.AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONObject response = new JSONObject(output);
                        String resultedMessage = response.getString("responseType");
                        if (resultedMessage.equals("success")) {
                            Toast.makeText(ConfirmPurchase.this, item2.getText().toString() + " added to Bill", Toast.LENGTH_SHORT).show();
                        } else {
                            // response = response.getJSONObject("payload");
                            // String errorMessage = response.getString("message");
                            Toast.makeText(ConfirmPurchase.this, item2.getText().toString() + " not bought", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        // Toast.makeText(MainActivity.this,""+ e, Toast.LENGTH_LONG).show();
                    }
                }
            }).execute();
        }

        if (item3.getText().toString() != "") {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("roll_number", GlobalVariables.currentRollNo);
                jsonObject.put("item_id", GlobalVariables.idItem3);
                jsonObject.put("quantity", dropdown3.getSelectedItem().toString());
                jsonObject.put("cost", GlobalVariables.PriceItem3 + "");
                jsonObject.put("counter_id", counter);
            } catch (Exception e) {
                Toast.makeText(ConfirmPurchase.this, "" + e, Toast.LENGTH_SHORT).show();
                // System.out.println("Exception in json encoding "+e);
            }
            new MyAsyncTask(ConfirmPurchase.this, jsonObject.toString(), URLS.Purchase_URL, new MyAsyncTask.AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONObject response = new JSONObject(output);
                        String resultedMessage = response.getString("responseType");
                        if (resultedMessage.equals("success")) {
                            Toast.makeText(ConfirmPurchase.this, item3.getText().toString() + " added to Bill", Toast.LENGTH_SHORT).show();
                        } else {
                            // response = response.getJSONObject("payload");
                            // String errorMessage = response.getString("message");
                            Toast.makeText(ConfirmPurchase.this, item3.getText().toString() + " not bought", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        // Toast.makeText(MainActivity.this,""+ e, Toast.LENGTH_LONG).show();
                    }
                }
            }).execute();
        }

        if (item4.getText().toString() != "") {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("roll_number", GlobalVariables.currentRollNo);
                jsonObject.put("item_id", GlobalVariables.idItem4);
                jsonObject.put("quantity", dropdown4.getSelectedItem().toString());
                jsonObject.put("cost", GlobalVariables.PriceItem4 + "");
                jsonObject.put("counter_id", counter);
            } catch (Exception e) {
                Toast.makeText(ConfirmPurchase.this, "" + e, Toast.LENGTH_SHORT).show();
                // System.out.println("Exception in json encoding "+e);
            }
            new MyAsyncTask(ConfirmPurchase.this, jsonObject.toString(), URLS.Purchase_URL, new MyAsyncTask.AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONObject response = new JSONObject(output);
                        String resultedMessage = response.getString("responseType");
                        if (resultedMessage.equals("success")) {
                            Toast.makeText(ConfirmPurchase.this, item4.getText().toString() + " added to Bill", Toast.LENGTH_SHORT).show();
                        } else {
                            // response = response.getJSONObject("payload");
                            // String errorMessage = response.getString("message");
                            Toast.makeText(ConfirmPurchase.this, item4.getText().toString() + " not bought", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        // Toast.makeText(MainActivity.this,""+ e, Toast.LENGTH_LONG).show();
                    }
                }
            }).execute();
        }

        if (item5.getText().toString() != "") {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("roll_number", GlobalVariables.currentRollNo);
                jsonObject.put("item_id", GlobalVariables.idItem5);
                jsonObject.put("quantity", dropdown5.getSelectedItem().toString());
                jsonObject.put("cost", GlobalVariables.PriceItem5 + "");
                jsonObject.put("counter_id", counter);
            } catch (Exception e) {
                Toast.makeText(ConfirmPurchase.this, "" + e, Toast.LENGTH_SHORT).show();
                // System.out.println("Exception in json encoding "+e);
            }
            new MyAsyncTask(ConfirmPurchase.this, jsonObject.toString(), URLS.Purchase_URL, new MyAsyncTask.AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONObject response = new JSONObject(output);
                        String resultedMessage = response.getString("responseType");
                        if (resultedMessage.equals("success")) {
                            Toast.makeText(ConfirmPurchase.this, item5.getText().toString() + " added to Bill", Toast.LENGTH_SHORT).show();
                        } else {
                            // response = response.getJSONObject("payload");
                            // String errorMessage = response.getString("message");
                            Toast.makeText(ConfirmPurchase.this, item5.getText().toString() + " not bought", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        // Toast.makeText(MainActivity.this,""+ e, Toast.LENGTH_LONG).show();
                    }
                }
            }).execute();
        }

        if (item6.getText().toString() != "") {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("roll_number", GlobalVariables.currentRollNo);
                jsonObject.put("item_id", GlobalVariables.idItem6);
                jsonObject.put("quantity", dropdown6.getSelectedItem().toString());
                jsonObject.put("cost", GlobalVariables.PriceItem6 + "");
                jsonObject.put("counter_id", counter);
            } catch (Exception e) {
                Toast.makeText(ConfirmPurchase.this, "" + e, Toast.LENGTH_SHORT).show();
                // System.out.println("Exception in json encoding "+e);
            }
            new MyAsyncTask(ConfirmPurchase.this, jsonObject.toString(), URLS.Purchase_URL, new MyAsyncTask.AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONObject response = new JSONObject(output);
                        String resultedMessage = response.getString("responseType");
                        if (resultedMessage.equals("success")) {
                            Toast.makeText(ConfirmPurchase.this, item6.getText().toString() + " added to Bill", Toast.LENGTH_SHORT).show();
                        } else {
                            // response = response.getJSONObject("payload");
                            // String errorMessage = response.getString("message");
                            Toast.makeText(ConfirmPurchase.this, item6.getText().toString() + " not bought", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        // Toast.makeText(MainActivity.this,""+ e, Toast.LENGTH_LONG).show();
                    }
                }
            }).execute();
        }

        if (item7.getText().toString() != "") {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("roll_number", GlobalVariables.currentRollNo);
                jsonObject.put("item_id", GlobalVariables.idItem7);
                jsonObject.put("quantity", dropdown7.getSelectedItem().toString());
                jsonObject.put("cost", GlobalVariables.PriceItem7 + "");
                jsonObject.put("counter_id", counter);
            } catch (Exception e) {
                Toast.makeText(ConfirmPurchase.this, "" + e, Toast.LENGTH_SHORT).show();
                // System.out.println("Exception in json encoding "+e);
            }
            new MyAsyncTask(ConfirmPurchase.this, jsonObject.toString(), URLS.Purchase_URL, new MyAsyncTask.AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONObject response = new JSONObject(output);
                        String resultedMessage = response.getString("responseType");
                        if (resultedMessage.equals("success")) {
                            Toast.makeText(ConfirmPurchase.this, item7.getText().toString() + " added to Bill", Toast.LENGTH_SHORT).show();
                        } else {
                            // response = response.getJSONObject("payload");
                            // String errorMessage = response.getString("message");
                            Toast.makeText(ConfirmPurchase.this, item7.getText().toString() + " not bought", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        // Toast.makeText(MainActivity.this,""+ e, Toast.LENGTH_LONG).show();
                    }
                }
            }).execute();
        }

        if (others.equals("1")) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("roll_number", GlobalVariables.currentRollNo);
                jsonObject.put("item_id", "10");
                jsonObject.put("quantity", dropdownOthers.getSelectedItem().toString());
                jsonObject.put("cost", GlobalVariables.PriceItemOthers + "");
                jsonObject.put("counter_id", counter);
                jsonObject.put("name", otherName);

            } catch (Exception e) {
                Toast.makeText(ConfirmPurchase.this, "" + e, Toast.LENGTH_SHORT).show();
                // System.out.println("Exception in json encoding "+e);
            }
            new MyAsyncTask(ConfirmPurchase.this, jsonObject.toString(), URLS.Purchase_URL, new MyAsyncTask.AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONObject response = new JSONObject(output);
                        String resultedMessage = response.getString("responseType");
                        if (resultedMessage.equals("success")) {
                            Toast.makeText(ConfirmPurchase.this, cbItem.getText().toString() + " added to Bill", Toast.LENGTH_SHORT).show();
                        } else {
                            // response = response.getJSONObject("payload");
                            // String errorMessage = response.getString("message");
                            Toast.makeText(ConfirmPurchase.this, cbItem.getText().toString() + " not bought", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        // Toast.makeText(MainActivity.this,""+ e, Toast.LENGTH_LONG).show();
                    }
                }
            }).execute();
        }

        GlobalVariables.totalItem1 = 0;
        GlobalVariables.QuantityItem1 = 0;
        GlobalVariables.PriceItem1 = 0;
        GlobalVariables.totalItem2 = 0;
        GlobalVariables.QuantityItem2 = 0;
        GlobalVariables.PriceItem2 = 0;
        GlobalVariables.totalItem3 = 0;
        GlobalVariables.QuantityItem3 = 0;
        GlobalVariables.PriceItem3 = 0;
        GlobalVariables.totalItem4 = 0;
        GlobalVariables.QuantityItem4 = 0;
        GlobalVariables.PriceItem4 = 0;
        GlobalVariables.totalItem5 = 0;
        GlobalVariables.QuantityItem5 = 0;
        GlobalVariables.PriceItem5 = 0;
        GlobalVariables.totalItem6 = 0;
        GlobalVariables.QuantityItem6 = 0;
        GlobalVariables.PriceItem6 = 0;
        GlobalVariables.totalItem7 = 0;
        GlobalVariables.QuantityItem7 = 0;
        GlobalVariables.PriceItem7 = 0;
        GlobalVariables.totalItemOthers = 0;
        GlobalVariables.QuantityItemOthers = 0;
        GlobalVariables.PriceItemOthers = 0;
        GlobalVariables.idItem1 = null;
        GlobalVariables.idItem2 = null;
        GlobalVariables.idItem3 = null;
        GlobalVariables.idItem4 = null;
        GlobalVariables.idItem5 = null;
        GlobalVariables.idItem6 = null;
        GlobalVariables.idItem7 = null;

        progressBar.setVisibility(View.VISIBLE);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("roll_number", GlobalVariables.currentRollNo);
        } catch (Exception e) {
            Toast.makeText(ConfirmPurchase.this, "" + e, Toast.LENGTH_LONG).show();
            // System.out.println("Exception in json encoding "+e);
        }
        new MyAsyncTask(ConfirmPurchase.this, jsonObject.toString(), URLS.CurrentBill_URL, new MyAsyncTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject response = new JSONObject(output);
                    String resultedMessage = response.getString("responseType");
                    if (resultedMessage.equals("success")) {
                        response = response.getJSONObject("payload");
                        String latestBill = response.getString("bill_amount");
                        GlobalVariables.currentMessBill = latestBill;
                        startActivity(new Intent(ConfirmPurchase.this, MessMenu.class));
                    } else {
                        response = response.getJSONObject("payload");
                        String errorMessage = response.getString("message");
                        Toast.makeText(ConfirmPurchase.this, errorMessage, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    // Toast.makeText(MainActivity.this,""+ e, Toast.LENGTH_LONG).show();
                }
            }
        }).execute();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (onStartCount > 1) {
            this.overridePendingTransition(R.anim.anim_slide_in_right,
                    R.anim.anim_slide_out_right);

        } else if (onStartCount == 1) {
            onStartCount++;
        }
    }
}
