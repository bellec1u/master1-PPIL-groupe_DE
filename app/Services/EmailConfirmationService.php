<?php

namespace App\Services;

use Mail;
use App\Models\User;

class EmailConfirmationService
{

    protected $userModel;

    public function __construct(User $userModel)
    {
        $this->userModel = $userModel;
    }


    /**
     * @return a random token of size 30.
     */
    private function getToken() {
        return str_random(60);
    }

    /**
     * Sets a validation code for email confirmation and sends an email to the
     * user.
     *
     * @param array $inputs
     * @return array with the validation_code
     */
    public function sendConfirmationMail(Array $inputs)
    {
        $token = $this->getToken();

        if (!array_key_exists('validation_code', $inputs)) {
            $inputs = array_merge($inputs, ['validation_code' => $token]);
        } else {
            $inputs['validation_code'] = $token;
        }

        $this->sendMail($inputs['email'], $token);

        return $inputs;
    }

    /**
     * Confirms user email by setting email_validation to true.
     *
     * @param $token
     * @return true if email validated, false if token doesn't exist
     */
    public function confirmEmail($token)
    {
        $user = $this->userModel->where("validation_code", "=", $token)->first();
        if (!$user) {
            return null;
        }

        $user->email_validated = true;
        $user->validation_code = null;
        $user->save();

        return $user;
    }

    public function resendConfirmationMail($user_id)
    {
        $user = $this->userModel->find($user_id);
        $token = $user->validation_code;
        $email = $user->email;
        $this->sendMail($email, $token);
    }

    private function sendMail($email, $token)
    {
        Mail::send('user.email_confirmation', compact('token'), function($message) use ($email) {
            $message->to($email)->subject('Vérifiez votre adresse mail');
        });
    }

}