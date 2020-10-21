package es.iessaladillo.pedrojoya.exchange

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.iessaladillo.pedrojoya.exchange.databinding.MainActivityBinding
import es.iessaladillo.pedrojoya.exchange.utils.SoftInputUtils

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViews()
    }

    private fun setUpViews() {
        binding.lblAmount.setTextColor(resources.getColor(R.color.pink_200))
        binding.rdgFromCurrency.setOnCheckedChangeListener { _, _ -> updateViews() }
        binding.rdgToCurrency.setOnCheckedChangeListener { _, _ -> updateViews() }
        binding.btnExchange.setOnClickListener { message() }
        binding.txtAmount.setOnEditorActionListener { _, _, _ ->
            message()
            true
        }
       /*binding.txtAmount.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                //No conseguía que funcionase, si escribia cualquier dígito en el editText, petaba la aplicación.
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                TODO("Not yet implemented")
            }

        })*/
    }

    private fun updateViews() {
        if(binding.rdbFromDollar.isChecked){
            binding.imgFrom.setImageResource(Currency.DOLLAR.drawableResId)
        } else if(binding.rdbFromEuro.isChecked){
            binding.imgFrom.setImageResource(Currency.EURO.drawableResId)
        } else{
            binding.imgFrom.setImageResource(Currency.POUND.drawableResId)
        }

        if(binding.rdbToDollar.isChecked){
            binding.imgTo.setImageResource(Currency.DOLLAR.drawableResId)
        } else if(binding.rdbToEuro.isChecked){
            binding.imgTo.setImageResource(Currency.EURO.drawableResId)
        } else{
            binding.imgTo.setImageResource(Currency.POUND.drawableResId)
        }

        enabledButtons()
    }

    private fun enabledButtons() {
        if (binding.rdbFromEuro.isChecked){
            binding.rdbToEuro.isEnabled = false
            binding.rdbToPound.isEnabled = true
            binding.rdbToDollar.isEnabled = true
        } else if (binding.rdbFromDollar.isChecked){
            binding.rdbToEuro.isEnabled = true
            binding.rdbToPound.isEnabled = true
            binding.rdbToDollar.isEnabled = false
        } else {
            binding.rdbToEuro.isEnabled = true
            binding.rdbToPound.isEnabled = false
            binding.rdbToDollar.isEnabled = true
        }

        if (binding.rdbToEuro.isChecked){
            binding.rdbFromEuro.isEnabled = false
            binding.rdbFromPound.isEnabled = true
            binding.rdbFromDollar.isEnabled = true
        } else if (binding.rdbToDollar.isChecked){
            binding.rdbFromEuro.isEnabled = true
            binding.rdbFromPound.isEnabled = true
            binding.rdbFromDollar.isEnabled = false
        } else {
            binding.rdbFromEuro.isEnabled = true
            binding.rdbFromPound.isEnabled = false
            binding.rdbFromDollar.isEnabled = true
        }
    }

    private fun message() {
        var message = ""
        var amount = binding.txtAmount.text.toString().toDouble()
        var newAmount = 0.0
        SoftInputUtils.hideSoftKeyboard(binding.lblAmount)

        if(binding.rdbFromDollar.isChecked && binding.rdbToEuro.isChecked){
            newAmount = amount * Currency.EURO.asDollar
            message = binding.txtAmount.text.toString() + Currency.DOLLAR.symbol + " = " + newAmount + Currency.EURO.symbol
        } else if(binding.rdbFromDollar.isChecked && binding.rdbToPound.isChecked) {
            newAmount = amount * Currency.POUND.asDollar
            message = binding.txtAmount.text.toString() + Currency.DOLLAR.symbol + " = " + newAmount + Currency.POUND.symbol
        } else if(binding.rdbFromEuro.isChecked && binding.rdbToDollar.isChecked){
            newAmount = amount / Currency.EURO.asDollar
            message = binding.txtAmount.text.toString() + Currency.EURO.symbol + " = " + newAmount + Currency.DOLLAR.symbol
        } else if(binding.rdbFromEuro.isChecked && binding.rdbToPound.isChecked){
            newAmount = (amount / Currency.EURO.asDollar) * Currency.POUND.asDollar
            message = binding.txtAmount.text.toString() + Currency.EURO.symbol + " = " + newAmount + Currency.POUND.symbol
        } else if (binding.rdbFromPound.isChecked && binding.rdbToDollar.isChecked){
            newAmount = amount / Currency.POUND.asDollar
            message = binding.txtAmount.text.toString() + Currency.POUND.symbol + " = " + newAmount + Currency.DOLLAR.symbol
        } else {
            newAmount = (amount / Currency.POUND.asDollar) * Currency.EURO.asDollar
            message = binding.txtAmount.text.toString() + Currency.POUND.symbol + " = " + newAmount + Currency.EURO.symbol
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}