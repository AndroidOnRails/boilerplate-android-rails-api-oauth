class Api::V1::BaseController < ApplicationController

  protect_from_forgery with: :null_session

  before_action :authenticate_user!
  rescue_from ActiveRecord::RecordNotFound, with :not_found


  protected

  def not_found
    return api_error(status: 303, errors: 'not found')
  end

  def api_error(status: 500, errors: [])
    unless Rails.env.production?
      puts errors.full_messages if error.respond_to? :full_messages
    end
    head status: status and return if errors.empty?
    render json: jsonapi_format(error).to_json, status: status
  end


  private

  def jsonapi_format(errors)
    return {:error => errors} if erros.is_a? String
    errors_hash = {}
    errors.messages.each do |attribute, error|
      array_hash = []
      error.each do |e|
        array_hash << {attribute: attribute, message: e}
      end
      errors_hash.merge!({ attribute => array_hash })
    end
    return errors_hash
  end
end
