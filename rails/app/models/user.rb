class User < ActiveRecord::Base
  has_many :authentication_tokens
  has_many :identities

  # Include default devise modules. Others available are:
  # :confirmable, :lockable, :timeoutable and :omniauthable
  devise :database_authenticatable, :registerable,
         :recoverable, :rememberable, :trackable, :validatable,
         :token_authenticatable,
         :omniauthable, :omniauth_providers => [:google_oauth2]

  def self.from_omniauth(auth)
    identity = Identity.find_with_omniauth(auth)

    if identity.nil?
      # No identity exists
      where(email: auth.info.email).first_or_create do |user|
        user.password = Devise.friendly_token[0,20]
        user.fullname = auth.info.name if auth.info.key?("name")  # assuming the user model has a name
        user.image = auth.info.image if auth.info.key?("image")  # assuming the user model has an image
        Identity.create_with_omniauth(auth, user)
      end
    elsif identity.user.nil?
      # No user is associatied with the identity
      user = where(email: auth.info.email).first_or_create do |user|
        user.password = Devise.friendly_token[0,20]
        user.fullname = auth.info.name if auth.info.key?("name")  # assuming the user model has a name
        user.image = auth.info.image if auth.info.key?("image")  # assuming the user model has an image
      end

      identity.update_attribute(:user_id, user.id)
      user
    else
      user = identity.user
    end

    # Originally, we simply saved the identity with the user model, which worked well, until we wanted to add new
    # oAuth providers. The above now resolves this problem.

    #where(provider: auth.provider, uid: auth.uid).first_or_create do |user|
    #  user.email = auth.info.email
    #  user.password = Devise.friendly_token[0,20]
    #  user.fullname = auth.info.name if auth.info.key?("name")  # assuming the user model has a name
    #  user.image = auth.info.image if auth.info.key?("image")  # assuming the user model has an image
    #end
  end
end
