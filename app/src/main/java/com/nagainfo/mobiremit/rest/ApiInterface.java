package com.nagainfo.mobiremit.rest;

import com.nagainfo.mobiremit.model.BankBranch.BankBranchData;
import com.nagainfo.mobiremit.model.BankBranch.BankBranchResultList;
import com.nagainfo.mobiremit.model.BeneficiaryBank.BenefBankData;
import com.nagainfo.mobiremit.model.BeneficiaryBank.BenefBankResultList;
import com.nagainfo.mobiremit.model.BeneficiaryBranch.BenefBranchData;
import com.nagainfo.mobiremit.model.BeneficiaryBranch.BenefBranchResultList;
import com.nagainfo.mobiremit.model.DrawingBank.BankResultList;
import com.nagainfo.mobiremit.model.Beneficiary.BeneficiaryData;
import com.nagainfo.mobiremit.model.Beneficiary.BeneficiaryList;
import com.nagainfo.mobiremit.model.Branches.BranchData;
import com.nagainfo.mobiremit.model.Branches.BranchResultList;
import com.nagainfo.mobiremit.model.Calculator.CalculatorData;
import com.nagainfo.mobiremit.model.Calculator.CalculatorResult;
import com.nagainfo.mobiremit.model.Cancel.CancelData;
import com.nagainfo.mobiremit.model.Cancel.CancelResult;
import com.nagainfo.mobiremit.model.ChangeCredentials.PassPinData;
import com.nagainfo.mobiremit.model.ChangeCredentials.PassPinResult;
import com.nagainfo.mobiremit.model.ChangePass.ChangePassData;
import com.nagainfo.mobiremit.model.ChangePass.ChangePassResult;
import com.nagainfo.mobiremit.model.ChangePin.ChangePinData;
import com.nagainfo.mobiremit.model.ChangePin.ChangePinResult;
import com.nagainfo.mobiremit.model.CodeType.CodeTypeList;
import com.nagainfo.mobiremit.model.Country.CountryList;
import com.nagainfo.mobiremit.model.Currency.CurrencyData;
import com.nagainfo.mobiremit.model.Currency.CurrencyResult;
import com.nagainfo.mobiremit.model.History.HistoryData;
import com.nagainfo.mobiremit.model.History.HistoryResultList;
import com.nagainfo.mobiremit.model.IDtype.IDtypeList;
import com.nagainfo.mobiremit.model.IncomeSource.IncomeSourceData;
import com.nagainfo.mobiremit.model.IncomeSource.IncomeSourceResultList;
import com.nagainfo.mobiremit.model.Login.LoginData;
import com.nagainfo.mobiremit.model.Login.LoginResultList;
import com.nagainfo.mobiremit.model.Nationality.NationalityList;
import com.nagainfo.mobiremit.model.News.NewsResultList;
import com.nagainfo.mobiremit.model.Purpose.PurposeData;
import com.nagainfo.mobiremit.model.Purpose.PurposeResultList;
import com.nagainfo.mobiremit.model.RateCharge.RateChargeData;
import com.nagainfo.mobiremit.model.RateCharge.RateChargeResultList;
import com.nagainfo.mobiremit.model.Rates.RatesList;
import com.nagainfo.mobiremit.model.Register.RegisterData;
import com.nagainfo.mobiremit.model.Register.ResgisterResult;
import com.nagainfo.mobiremit.model.RegisterNew.RegisterNewData;
import com.nagainfo.mobiremit.model.RegisterNew.RegisterNewResult;
import com.nagainfo.mobiremit.model.SaveBeneficiary.BeneficiaryDataList;
import com.nagainfo.mobiremit.model.SaveBeneficiary.BeneficiaryResult;
import com.nagainfo.mobiremit.model.SaveRemitance.SaveRemitanceData;
import com.nagainfo.mobiremit.model.SaveRemitance.SaveRemitanceResultList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface ApiInterface {

    @GET("GetIDType")
    Call<IDtypeList> getIdTypes();

    @GET("Nationality")
    Call<NationalityList> getNationality();

    @GET("Country")
    Call<CountryList> getCountry();

    @GET("GetDrawingBank")
    Call<BankResultList> GetDrawingBank();

    @POST("GetRate")
    Call<RatesList> getRates();

    @GET("GetNews")
    Call<NewsResultList> GetNews();

    @GET("TypeDetails")
    Call<CodeTypeList> GetBankCodeType();

    @POST("GetOrgBranch")
    Call<BranchResultList> getBranches(@Body BranchData body);

    @POST("SignUp")
    Call<ResgisterResult> SignUp(@Body RegisterData body);

    @POST("AddCustomer")
    Call<RegisterNewResult> SignUpNew(@Body RegisterNewData body);

    @POST("SignIn")
    Call<LoginResultList> SignIn(@Body LoginData body);

    @POST("ChangePasswdPin")
    Call<PassPinResult> ChangePasswdPin(@Body PassPinData body);

    @POST("ChangePin")
    Call<ChangePinResult> ChangePin(@Body ChangePinData body);

    @POST("ChangePassword")
    Call<ChangePassResult> ChangePassword(@Body ChangePassData body);

    @POST("GetBeneficiaryDetails")
    Call<BeneficiaryList> GetBeneficiary(@Body BeneficiaryData body);

    @POST("GetPurpose")
    Call<PurposeResultList> GetPurpose(@Body PurposeData body);

    @POST("GetSource")
    Call<IncomeSourceResultList> GetSource(@Body IncomeSourceData body);

    @POST("GetRateCharge")
    Call<RateChargeResultList> GetRateCharge(@Body RateChargeData body);

    @POST("SaveRemittance")
    Call<SaveRemitanceResultList> SaveRemittance(@Body SaveRemitanceData body);

    @POST("GetRemitHistory")
    Call<HistoryResultList> GetRemitHistory(@Body HistoryData body);

    @POST("CancelTrns")
    Call<CancelResult> CancelTrns(@Body CancelData body);

    @GET("Currency")
    Call<CurrencyResult> Currency();

    @POST("CurrencyCalc")
    Call<CalculatorResult> CurrencyCalc(@Body CalculatorData body);

    @POST("RemitCurrency")
    Call<CurrencyResult> RemitCurrency(@Body CurrencyData body);

    @POST("GetOrganization")
    Call<BenefBankResultList> GetBeneficiaryBank(@Body BenefBankData body);

    @POST("GetOrgBranch")
    Call<BenefBranchResultList> GetBeneficiaryBranch(@Body BenefBranchData body);

    @POST("GetBankBranch")
    Call<BankBranchResultList> GetBankBranch(@Body BankBranchData body);

    @POST("AddBeneficiary")
    Call<BeneficiaryResult> AddBeneficiary(@Body BeneficiaryDataList body);

}
