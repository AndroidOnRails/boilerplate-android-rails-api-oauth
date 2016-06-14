class Users::OmniauthCallbacksController < Devise::OmniauthCallbacksController

  def google_oauth2
    @user = User.from_omniauth(request.env["omniauth.auth"])
    sign_in @user
    if @user.persisted?
      render json: {:accessToken => Tiddle.create_and_return_token(@user, request), :boilerPlate => { profile: { name: @user.fullname, email: @user.email}  } }.to_json, :status => 200
    end

  end
end